package br.com.mv.liquibase.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import liquibase.change.ColumnConfig;
import liquibase.change.core.InsertDataChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.precondition.core.PreconditionContainer;
import liquibase.precondition.core.SqlPrecondition;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceNextValueFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.liquibase.model.CreateParametroForm;
import br.com.mv.liquibase.model.GrupoParametro;
import br.com.mv.liquibase.model.OpcaoParametro;
import br.com.mv.liquibase.model.Parametro;
import br.com.mv.liquibase.model.Versao;
import br.com.mv.liquibase.repository.GrupoParametroRepository;

import com.thoughtworks.xstream.XStream;

@Service
public class CreateParametroService {

	@Autowired
    private GrupoParametroRepository grupoParametroRepository;
	
	
	public void criarGrupoParametrosXML() throws IOException {
		File file = new File("src/main/resources/grupoParametros.xml");
		file.createNewFile();
		
		List<GrupoParametro> grupoParametros = grupoParametroRepository.findAll();
		
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        XStream xStream = new XStream();
        xStream.alias("grupoParametro", GrupoParametro.class);
        xStream.toXML(grupoParametros, outputStream);
    }

	public void salvar(CreateParametroForm createParametroForm) {
		Parametro parametro = createParametroForm.getParametro();
		
		DatabaseChangeLog databaseChangeLog = new DatabaseChangeLog();
		List<ChangeSet> listChangeSet = new ArrayList<>();
		
		InsertDataChange insertDataChange = new InsertDataChange();
		insertDataChange.setSchemaName("DBAMVFOR");
		insertDataChange.setTableName("MV_PARAMETRO");
		
		ColumnConfig columnConfigId = new ColumnConfig();
		columnConfigId.setName("ID");
		SequenceNextValueFunction valueSequenceNext = new SequenceNextValueFunction("DBAMVFOR.MV_SEQ_PARAMETROS");
		columnConfigId.setValueSequenceNext(valueSequenceNext);
		insertDataChange.addColumn(columnConfigId);
		
		ColumnConfig columnConfigChave = new ColumnConfig();
		columnConfigChave.setName("CHAVE");
		columnConfigChave.setValue(parametro.getChave());
		insertDataChange.addColumn(columnConfigChave);
		
		ColumnConfig columnConfigDescricao = new ColumnConfig();
		columnConfigDescricao.setName("DESCRICAO");
		columnConfigDescricao.setValue(parametro.getDescricao());
		insertDataChange.addColumn(columnConfigDescricao);
		
		ColumnConfig columnConfigValor = new ColumnConfig();
		columnConfigValor.setName("VALOR");
		columnConfigValor.setValue(parametro.getValor());
		insertDataChange.addColumn(columnConfigValor);
		
		ColumnConfig columnConfigGrupoParametro = new ColumnConfig();
		columnConfigGrupoParametro.setName("CD_GRUPO_PARAMETRO");
		
		if (parametro.getGrupoParametro() != null) {
			DatabaseFunction valueComputedGrupoParametro = new DatabaseFunction("(SELECT CD_GRUPO_PARAMETRO FROM DBAMVFOR.MV_GRUPO_PARAMETRO WHERE TRIM(DS_DESCRICAO) = '" + parametro.getGrupoParametro().getDescricao().trim() + "')");
			columnConfigGrupoParametro.setValueComputed(valueComputedGrupoParametro);
		}
		
		insertDataChange.addColumn(columnConfigGrupoParametro);
		
		ColumnConfig columnConfigTela = new ColumnConfig();
		columnConfigTela.setName("TELA");
		columnConfigTela.setValue(parametro.getTela());
		insertDataChange.addColumn(columnConfigTela);
		
		ColumnConfig columnConfigObrigatorio = new ColumnConfig();
		columnConfigObrigatorio.setName("OBRIGATORIO");
		columnConfigObrigatorio.setValueBoolean(parametro.getTipoObrigatorio());
		insertDataChange.addColumn(columnConfigObrigatorio);

		ColumnConfig columnConfigTipoParametro = new ColumnConfig();
		columnConfigTipoParametro.setName("TP_PARAMETRO");
		columnConfigTipoParametro.setValueNumeric(parametro.getTipoParametro());
		insertDataChange.addColumn(columnConfigTipoParametro);
		
		ColumnConfig columnConfigObjetivo = new ColumnConfig();
		columnConfigObjetivo.setName("DS_OBJETIVO");
		columnConfigObjetivo.setValue(parametro.getDescricaoObjetivo());
		insertDataChange.addColumn(columnConfigObjetivo);
		
		if (parametro.getArquivo() != null && parametro.getNomeArquivo() != null) {
			ColumnConfig columnConfigArquivo = new ColumnConfig();
			columnConfigArquivo.setName("LO_ARQUIVO");
			
            FileOutputStream outPut;
			try {
				outPut = new FileOutputStream("src/main/resources/db/changelog/inserts/arquivos/" + parametro.getNomeArquivo());
				outPut.write(parametro.getArquivo());
				outPut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			columnConfigArquivo.setValueBlobFile("arquivos/" + parametro.getNomeArquivo());
			insertDataChange.addColumn(columnConfigArquivo);
        }
		
		ColumnConfig columnConfigNomeArquivo = new ColumnConfig();
		columnConfigNomeArquivo.setName("NM_ARQUIVO");
		columnConfigNomeArquivo.setValue(parametro.getNomeArquivo());
		insertDataChange.addColumn(columnConfigNomeArquivo);
		
		ColumnConfig columnConfigTipoArquivo = new ColumnConfig();
		columnConfigTipoArquivo.setName("TP_MIME_ARQUIVO");
		columnConfigTipoArquivo.setValue(parametro.getTipoMimeArquivo());
		insertDataChange.addColumn(columnConfigTipoArquivo);
		
		
		ChangeSet changeSetParametro = new ChangeSet("insert_mv_parametro_" + parametro.getChave(), "mvfor", false, false, null, null, null, true, databaseChangeLog);
		PreconditionContainer preconditionContainer = new PreconditionContainer();
		preconditionContainer.setOnFail("MARK_RAN");
		
		SqlPrecondition sqlPrecondition = new SqlPrecondition();
		sqlPrecondition.setExpectedResult("0");
		sqlPrecondition.setSql("SELECT COUNT(1) FROM DBAMVFOR.MV_PARAMETRO WHERE CHAVE = '" + parametro.getChave() + "'");
		
		preconditionContainer.addNestedPrecondition(sqlPrecondition);
		
		changeSetParametro.setPreconditions(preconditionContainer);
		changeSetParametro.setComments("Inclusão do parâmetro " + parametro.getChave());
		changeSetParametro.addChange(insertDataChange);
		
		databaseChangeLog.addChangeSet(changeSetParametro);
		listChangeSet.add(changeSetParametro);
		
		
		parametro.getOpcoesParametros().size();
		
		for (OpcaoParametro opcaoParametro : parametro.getOpcoesParametros()) {
			InsertDataChange insertDataChangeOpcaoParametro = new InsertDataChange();
			insertDataChangeOpcaoParametro.setSchemaName("DBAMVFOR");
			insertDataChangeOpcaoParametro.setTableName("OPCAO_PARAMETRO");
			
			ColumnConfig columnConfigIdOpcaoParametro = new ColumnConfig();
			columnConfigIdOpcaoParametro.setName("CD_OPCAO_PARAMETRO");
			SequenceNextValueFunction valueSequenceNextOpcaoParametro = new SequenceNextValueFunction("DBAMVFOR.SEQ_OPCAO_PARAMETRO");
			columnConfigIdOpcaoParametro.setValueSequenceNext(valueSequenceNextOpcaoParametro);
			insertDataChangeOpcaoParametro.addColumn(columnConfigIdOpcaoParametro);
			
			ColumnConfig columnConfigParametro = new ColumnConfig();
			columnConfigParametro.setName("CD_PARAMETRO");
			DatabaseFunction valueComputed = new DatabaseFunction("(SELECT ID FROM DBAMVFOR.MV_PARAMETRO WHERE TRIM(CHAVE) = '" + parametro.getChave().trim() + "')");
			columnConfigParametro.setValueComputed(valueComputed);
			insertDataChangeOpcaoParametro.addColumn(columnConfigParametro);
			
			ColumnConfig columnConfigDescricaoOpcaoParametro = new ColumnConfig();
			columnConfigDescricaoOpcaoParametro.setName("DS_OPCAO_PARAMETRO");
			columnConfigDescricaoOpcaoParametro.setValue(opcaoParametro.getDescricaoOpcaoParametro());
			insertDataChangeOpcaoParametro.addColumn(columnConfigDescricaoOpcaoParametro);
			
			ColumnConfig columnConfigFlagAtivo = new ColumnConfig();
			columnConfigFlagAtivo.setName("SN_ATIVO");
			columnConfigFlagAtivo.setValueBoolean(opcaoParametro.getFlagAtivo());
			insertDataChangeOpcaoParametro.addColumn(columnConfigFlagAtivo);

			
			ChangeSet changeSetOpcaoParametro = new ChangeSet("insert_opcao_parametro_" + opcaoParametro.getParametro().getChave() + "_" + opcaoParametro.getDescricaoOpcaoParametro(), "mvfor", false, false, null, null, null, true, databaseChangeLog);
			
			PreconditionContainer preconditionContainerOpcaoParametro = new PreconditionContainer();
			preconditionContainerOpcaoParametro.setOnFail("MARK_RAN");
			
			SqlPrecondition sqlPreconditionOpcaoParametro = new SqlPrecondition();
			sqlPreconditionOpcaoParametro.setExpectedResult("0");
			sqlPreconditionOpcaoParametro.setSql("SELECT COUNT(1) FROM DBAMVFOR.OPCAO_PARAMETRO WHERE TRIM(DS_OPCAO_PARAMETRO) = '" + opcaoParametro.getDescricaoOpcaoParametro().trim() + "' AND CD_PARAMETRO = (SELECT ID FROM DBAMVFOR.MV_PARAMETRO WHERE TRIM(CHAVE) = '" + parametro.getChave().trim() + "')");
			
			preconditionContainerOpcaoParametro.addNestedPrecondition(sqlPreconditionOpcaoParametro);
			
			changeSetOpcaoParametro.setPreconditions(preconditionContainerOpcaoParametro);
			changeSetOpcaoParametro.setComments("Inclusão da opção parâmetro " + opcaoParametro.getDescricaoOpcaoParametro());
			changeSetOpcaoParametro.addChange(insertDataChangeOpcaoParametro);
			
			databaseChangeLog.addChangeSet(changeSetOpcaoParametro);
			listChangeSet.add(changeSetOpcaoParametro);
		}
		
		
		try (OutputStream os = new FileOutputStream("src/main/resources/db/changelog/inserts/" + Versao.getVersaoAtual() + "_" + LocalDate.now() + "_create_parametro_" + parametro.getChave() + " .xml")) {
			XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
			xmlChangeLogSerializer.write(listChangeSet, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
