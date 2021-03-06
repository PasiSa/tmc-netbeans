package fi.helsinki.cs.tmc.data.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import fi.helsinki.cs.tmc.data.SubmissionResult;
import fi.helsinki.cs.tmc.stylerunner.validation.CheckstyleResult;
import fi.helsinki.cs.tmc.testrunner.StackTraceSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class SubmissionResultParser {

    public SubmissionResult parseFromJson(final String json) {

        if (json.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty input");
        }

        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(SubmissionResult.Status.class, new StatusDeserializer())
                    .registerTypeAdapter(StackTraceElement.class, new StackTraceSerializer())
                    .create();

            SubmissionResult result = gson.fromJson(json, SubmissionResult.class);

            // Parse validations field from JSON
            JsonObject output = new JsonParser().parse(json).getAsJsonObject();
            JsonElement validationElement = output.get("validations");

            if (validationElement != null) {
                result.setValidationResult(CheckstyleResult.build(validationElement.toString()));
            } else {
                result.setValidationResult(CheckstyleResult.build("{}"));
            }

            return result;

        } catch (RuntimeException runtimeException) {
            throw new RuntimeException("Failed to parse submission result: " + runtimeException.getMessage(),
                                                                               runtimeException);
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to parse submission result: " + ioException.getMessage(), ioException);
        }
    }

    private static class StatusDeserializer implements JsonDeserializer<SubmissionResult.Status> {
        @Override
        public SubmissionResult.Status deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            String s = json.getAsJsonPrimitive().getAsString();
            try {
                return SubmissionResult.Status.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Unknown submission status: " + s);
            }
        }
    }
}
