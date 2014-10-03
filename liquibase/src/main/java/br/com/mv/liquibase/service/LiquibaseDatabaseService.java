package br.com.mv.liquibase.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.logging.LogFactory;
import liquibase.resource.AbstractResourceAccessor;
import oracle.jdbc.pool.OracleDataSource;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;

@Service
public class LiquibaseDatabaseService {
	
	public void testeConexao() throws SQLException, LiquibaseException {
		OracleDataSource oracleDataSource = new OracleDataSource();
		oracleDataSource.setUser("dbamvfor");
		oracleDataSource.setPassword("dbamvfor");
		oracleDataSource.setURL("jdbc:oracle:thin:@192.168.254.17:1521:hml4");
		
		
		Database dataBase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(oracleDataSource.getConnection()));
		Liquibase liquibase = new Liquibase("classpath:/db/changelog/db.changelog-master.xml", new SpringResourceOpener("classpath:/db/changelog/db.changelog-master.xml") , dataBase);
		
		Writer writer = null;
		try(OutputStream os = new FileOutputStream("src/main/resources/db/doc/relatorio.txt")) {
			writer = new OutputStreamWriter(os);
			liquibase.reportStatus(true, new Contexts(), writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//liquibase.generateDocumentation("src/main/resources/db/doc");
	}

	public class SpringResourceOpener extends AbstractResourceAccessor {

		private ResourceLoader resourceLoader = new DefaultResourceLoader();
        private String parentFile;
        public SpringResourceOpener(String parentFile) {
            this.parentFile = parentFile;
        }

        @Override
        protected void init() {
            super.init();
            try {
                Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("");
                for (Resource res : resources) {
                    addRootPath(res.getURL());
                }
            } catch (IOException e) {
                LogFactory.getInstance().getLog().warning("Error initializing SpringLiquibase", e);
            }
        }

        @Override
        public Set<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories, boolean recursive) throws IOException {
            Set<String> returnSet = new HashSet<String>();

			Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(adjustClasspath(path));

			for (Resource res : resources) {
                File file = res.getFile();
                getContents(file, recursive, includeFiles, includeDirectories, path, returnSet);
			}

            return returnSet;
		}

        @Override
        public Set<InputStream> getResourcesAsStream(String path) throws IOException {
            Set<InputStream> returnSet = new HashSet<InputStream>();
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(adjustClasspath(path));

            if (resources == null || resources.length == 0) {
                return null;
            }
            for (Resource resource : resources) {
                returnSet.add(resource.getURL().openStream());
            }

            return returnSet;
		}

		public Resource getResource(String file) {
			return resourceLoader.getResource(adjustClasspath(file));
		}

		private String adjustClasspath(String file) {
			return isPrefixPresent(parentFile) && !isPrefixPresent(file) ? ResourceLoader.CLASSPATH_URL_PREFIX + file : file;
		}

		public boolean isPrefixPresent(String file) {
			if (file.startsWith("classpath") || file.startsWith("file:") || file.startsWith("url:")) {
				return true;
			}
			return false;
		}

		@Override
        public ClassLoader toClassLoader() {
			if (resourceLoader == null) {
				resourceLoader = new DefaultResourceLoader();
			}
			
			return resourceLoader.getClassLoader();
		}
	}
}
