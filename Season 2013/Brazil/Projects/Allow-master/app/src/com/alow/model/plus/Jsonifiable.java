/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alow.model.plus;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Date;

import lombok.NoArgsConstructor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Adds JSON serialization and deserialization to a class.  Uses Gson as the
 * underlying serialization mechanism.  Also adds java.util.Date serialization
 * and deserialization.
 *
 * @author vicfryzel@google.com (Vic Fryzel)
 * @author cartland@google.com (Chris Cartland)
 */
@NoArgsConstructor
public abstract class Jsonifiable {
  public static String kind = "photohunt#jsonifiable";

  /**
   * JSON serializer for java.util.Date, required when serializing larger
   * objects containing Date members.
   */
  public static final JsonSerializer<Date> DATE_SERIALIZER
  = new JsonSerializer<Date>() {
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc,
        JsonSerializationContext context) {
      try {
        return new JsonPrimitive(src.getTime());
      } catch (NullPointerException e) {
        return null;
      }
    }
  };

  /**
   * JSON deserializer for java.util.Date, required when deserializing larger
   * objects containing Date members.
   */
  public static final JsonDeserializer<Date> DATE_DESERIALIZER
  = new JsonDeserializer<Date>() {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      try {
        return new Date(json.getAsLong());
      } catch (NullPointerException e) {
        return null;
      }
    }
  };

  /**
   * Gson object to use in all serialization and deserialization.
   */
  public static final Gson GSON = new GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation()
      .registerTypeAdapter(Date.class, Jsonifiable.DATE_SERIALIZER)
      .registerTypeAdapter(Date.class, Jsonifiable.DATE_DESERIALIZER)
      .create();

  /**
   * @param json Object to convert to instance representation.
   * @param clazz Type to which object should be converted.
   * @return Instance representation of the given JSON object.
   */
  public static <T> T fromJson(String json, Class<T> clazz) {
    return GSON.fromJson(json, clazz);
  }

  /**
   * @param reader Reader from which to read JSON string.
   * @param clazz Type to which object should be converted.
   * @return Instance representation of the given JSON object.
   */
  public static <T> T fromJson(Reader reader, Class<T> clazz) {
    return GSON.fromJson(reader, clazz);
  }

  /**
   * @return JSON representation of this instance.
   */
  public String toJson() {
    return GSON.toJson(this);
  }

  /**
   * @return this.toJson()
   */
  @Override
  public String toString() {
    return toJson();
  }
}
