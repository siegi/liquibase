package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.statement.core.ClearDatabaseChangeLogTableStatement;

public class ClearDatabaseChangeLogTableGenerator implements SqlGenerator<ClearDatabaseChangeLogTableStatement> {
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }

    public boolean supports(ClearDatabaseChangeLogTableStatement statement, Database database) {
        return true;
    }

    public ValidationErrors validate(ClearDatabaseChangeLogTableStatement clearDatabaseChangeLogTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    public Sql[] generateSql(ClearDatabaseChangeLogTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        try {
        	String schemaName = null;
        	if (database.isPeculiarLiquibaseSchema()) {
        		schemaName = database.getLiquibaseSchemaName();
        	} else {
        		schemaName = database.convertRequestedSchemaToSchema(null);
        	}
            return new Sql[] {
                    new UnparsedSql("DELETE FROM " + database.escapeTableName(schemaName, database.getDatabaseChangeLogTableName()))
            };
        } catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}