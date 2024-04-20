package dev.stephenblevins.runnerz.users;

public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo
) {
}
