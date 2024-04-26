package com.babelgroup.renting.entities;

public enum RequestResult {
    APPROVED("Aprobada"),
    DENIED("Denegada"),
    APPROVED_WITH_WARRANTY("Aprobada con garantías"),
    PENDING("Pendiente a revisar"),
    PREAPPROVED("Preaprobada"),
    PREDENIED("Predenegada");

    private final String description;

    RequestResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public static String getDescriptionByName(String name) {
        for (RequestResult result : RequestResult.values()) {
            if (result.name().equalsIgnoreCase(name)) {
                return result.getDescription();
            }
        }
        throw new IllegalArgumentException("No enum constant " + RequestResult.class.getCanonicalName() + "." + name);
    }
}
