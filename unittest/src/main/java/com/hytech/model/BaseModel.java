package com.hytech.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class BaseModel {
	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static <T> T fromJsonToOwner(String json, Class<T> clz) {
		return new JSONDeserializer<T>().use(null, clz).deserialize(json);
	}

	public static <T> String toJsonArray(Collection<T> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static <T> Collection<T> fromJsonArrayToModel(String json, Class<T> clz) {
		return new JSONDeserializer<List<T>>().use(null, ArrayList.class)
				.use("values", clz).deserialize(json);
	}
}
