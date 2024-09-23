package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    HashMap<String, String> codeToCountry;
    HashMap<String, String> countryToCode;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            // Should have an interface or data package so that I know how "lines" will be structured after reading it.
            codeToCountry = new HashMap<String, String>();
            countryToCode = new HashMap<String, String>();
            this.parseFile(lines);
            System.out.println(codeToCountry);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void parseFile(List<String> file) {
        for (String row : file.subList(1, file.size())) {
            String[] rowList = row.split("\t");
            String countryName = rowList[0];
            String countryCode = rowList[2];
            this.countryToCode.put(countryName, countryCode);
            this.codeToCountry.put(countryCode, countryName);
        }
    }



    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    // Needs a data packet to specify input format of code and the format of the code in the txt file.
    public String fromCountryCode(String code) {
        String codeFormatted = code.toUpperCase();
        return codeToCountry.get(codeFormatted);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryToCode.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return codeToCountry.size();
    }
}
