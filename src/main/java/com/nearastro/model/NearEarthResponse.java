package com.nearastro.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

@Data
public class NearEarthResponse {
	private ObjectNode nearEarthObjects;
	private int elementCount;
	private Links links;
}