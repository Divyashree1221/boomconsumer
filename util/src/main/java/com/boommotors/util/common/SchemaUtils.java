/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.util.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author rjanumpally
 */
@Component
public class SchemaUtils {

    public ProcessingReport isValid(String json, String schema) throws Exception {
        JsonNode schemaNode = JsonLoader.fromString(schema);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema jsonSchema = factory.getJsonSchema(schemaNode);
        JsonNode jsonNode = JsonLoader.fromString(json);
        ProcessingReport report = jsonSchema.validate(jsonNode);
        //return report.isSuccess();
        return report;
    }
}
