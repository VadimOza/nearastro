package com.nearastro.model;

import java.util.Comparator;
import java.util.List;

import lombok.Data;

@Data
public class Asteroid {
    private String id;
    private String neoReferenceId;
    private String nasaJplUrl;
    private String name;
    private double absoluteMagnitudeH;
    private boolean isPotentiallyHazardousAsteroid;
    private boolean isSentryObject;
    private Links links;
    private EstimatedDiameter estimatedDiameter;
    private List<CloseApproachDataItem> closeApproachData;

    public String getMinDistance() {
        return closeApproachData.stream()
                .min(Comparator.comparing(o -> o.getMissDistance().getKilometers()))
                .map(closeApproachDataItem -> closeApproachDataItem.getMissDistance().getKilometers())
                .get(); // is this possible that this attribute is missing?
    }

    public double getMaxDiameter() {
        return estimatedDiameter.getKilometers().getEstimatedDiameterMax();
    }
}