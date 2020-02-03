package net.neuralm.client.neat;

import com.google.gson.*;
import net.neuralm.client.messages.serializer.JsonSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class OrganismTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/evaluateTest.csv")
    void getValue(double inA, double inB, double inC, double expected) {
        Organism organism = new Organism(Arrays.asList(

            new ConnectionGene(0, 3, 1, true),
            new ConnectionGene(1, 3, 1, false),
            new ConnectionGene(2, 3, 1, true),
            new ConnectionGene(1, 4, 1, true),
            new ConnectionGene(4, 3, 1, true),
            new ConnectionGene(0, 4, 1, true)

        ), 3, 1);

        Assertions.assertEquals(expected, organism.evaluate(new double[]{inA, inB, inC})[0], 0.0000000001);
    }

    @Test
    void parseJson() {
        String json = "{\"Organisms\":[{\"Id\":\"1110df35-52f0-4bb5-8ce4-c68de3f50717\",\"ConnectionGenes\":[{\"Id\":\"ab1f8a88-f22c-49ce-e69c-08d7a435cc56\",\"OrganismId\":\"1110df35-52f0-4bb5-8ce4-c68de3f50717\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":0.9468057444071425,\"Enabled\":true},{\"Id\":\"608c8dba-cb98-4e0d-e69d-08d7a435cc56\",\"OrganismId\":\"1110df35-52f0-4bb5-8ce4-c68de3f50717\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.8501794032054857,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"23b86cf7-9feb-4e83-9b66-52ee08f002bb\",\"Layer\":0,\"NodeIdentifier\":1},{\"Id\":\"b29a8f7e-e9a9-4bea-b177-c41556650edd\",\"Layer\":0,\"NodeIdentifier\":0}],\"OutputNodes\":[{\"Id\":\"cbf2e2d9-edb9-4a22-be06-fc7c81a08d9b\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"hah\",\"Generation\":2},{\"Id\":\"5f58a628-d6f9-4c89-894c-cc0cc67b4b58\",\"ConnectionGenes\":[{\"Id\":\"5e10a17e-d5ed-4f43-e6be-08d7a435cc56\",\"OrganismId\":\"5f58a628-d6f9-4c89-894c-cc0cc67b4b58\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.8874416885839037,\"Enabled\":true},{\"Id\":\"2ce5cf89-401f-45f4-e6bf-08d7a435cc56\",\"OrganismId\":\"5f58a628-d6f9-4c89-894c-cc0cc67b4b58\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":0.9468057444071425,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"04263382-c8a1-4d9b-ad8d-78ebd11d1634\",\"Layer\":0,\"NodeIdentifier\":1},{\"Id\":\"8f09d844-d41b-4aef-b518-bbb3b81ea4d1\",\"Layer\":0,\"NodeIdentifier\":0}],\"OutputNodes\":[{\"Id\":\"d9b4add4-8a76-4c13-91e4-30187428af54\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"huv\",\"Generation\":2},{\"Id\":\"233ca2a9-016a-4165-9f04-d0cd30599466\",\"ConnectionGenes\":[{\"Id\":\"de88f87c-a903-4d91-e6c6-08d7a435cc56\",\"OrganismId\":\"233ca2a9-016a-4165-9f04-d0cd30599466\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":-0.8599432335979972,\"Enabled\":true},{\"Id\":\"13c275df-3ca7-4ad7-e6c7-08d7a435cc56\",\"OrganismId\":\"233ca2a9-016a-4165-9f04-d0cd30599466\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.9279808662496418,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"aab6743b-bd78-4601-8f44-36ced34d525c\",\"Layer\":0,\"NodeIdentifier\":0},{\"Id\":\"6c38d04f-568d-4ed4-9030-652c5fdb3f0b\",\"Layer\":0,\"NodeIdentifier\":1}],\"OutputNodes\":[{\"Id\":\"f7a8d5ee-2833-4c80-9454-6ffdf5586b9c\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"meev\",\"Generation\":2},{\"Id\":\"a100859d-5c5f-4b32-bc4b-cf0f65744add\",\"ConnectionGenes\":[{\"Id\":\"84660026-932a-4d41-e6b8-08d7a435cc56\",\"OrganismId\":\"a100859d-5c5f-4b32-bc4b-cf0f65744add\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.5511769361566645,\"Enabled\":true},{\"Id\":\"8937ed99-3ea7-4fe0-e6b9-08d7a435cc56\",\"OrganismId\":\"a100859d-5c5f-4b32-bc4b-cf0f65744add\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":-0.6720582465045425,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"8f7a517c-39dc-421b-9718-163eb7c90d89\",\"Layer\":0,\"NodeIdentifier\":0},{\"Id\":\"ddc31fdb-cd5a-47b9-a256-7d43aaf7cb3e\",\"Layer\":0,\"NodeIdentifier\":1}],\"OutputNodes\":[{\"Id\":\"7428049a-dd70-4430-8ec1-6a3687d2814a\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"wopoom\",\"Generation\":2},{\"Id\":\"879c8309-4542-4882-9ae0-f49aaee1ded1\",\"ConnectionGenes\":[{\"Id\":\"08544f7b-eafb-4d78-e6b0-08d7a435cc56\",\"OrganismId\":\"879c8309-4542-4882-9ae0-f49aaee1ded1\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":-0.911530219908585,\"Enabled\":true},{\"Id\":\"8a42569d-1f19-421f-e6b1-08d7a435cc56\",\"OrganismId\":\"879c8309-4542-4882-9ae0-f49aaee1ded1\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.5396867401617051,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"ecf569b1-9cc1-46b2-a070-27462634b1ba\",\"Layer\":0,\"NodeIdentifier\":1},{\"Id\":\"cc5293ae-d3e2-41af-8d5f-e23472e10638\",\"Layer\":0,\"NodeIdentifier\":0}],\"OutputNodes\":[{\"Id\":\"80461bd3-e8e6-4150-a38c-c78ec6a3b0ff\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"kixityc\",\"Generation\":2},{\"Id\":\"dd44e9ea-6e1c-42f2-a713-de9f60f36e0f\",\"ConnectionGenes\":[{\"Id\":\"70978ca8-01a0-4ad3-e6ca-08d7a435cc56\",\"OrganismId\":\"dd44e9ea-6e1c-42f2-a713-de9f60f36e0f\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":0.3642462377269968,\"Enabled\":true},{\"Id\":\"269e36a6-4185-421c-e6cb-08d7a435cc56\",\"OrganismId\":\"dd44e9ea-6e1c-42f2-a713-de9f60f36e0f\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.8933658557913107,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"cf286c7e-df70-4f20-9705-81b37a71790b\",\"Layer\":0,\"NodeIdentifier\":0},{\"Id\":\"c65a7bc1-d0d4-4b1f-a495-f87cbadcf847\",\"Layer\":0,\"NodeIdentifier\":1}],\"OutputNodes\":[{\"Id\":\"c988fd4d-e05b-40a0-8e32-78f19fc5f751\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"haafeezaab\",\"Generation\":2},{\"Id\":\"e9e90ef8-5c77-4c4f-9214-eeecbc890cc5\",\"ConnectionGenes\":[{\"Id\":\"fcbf70b3-6255-448e-e6aa-08d7a435cc56\",\"OrganismId\":\"e9e90ef8-5c77-4c4f-9214-eeecbc890cc5\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":0.7500601238804219,\"Enabled\":true},{\"Id\":\"1646d659-0670-41cc-e6ab-08d7a435cc56\",\"OrganismId\":\"e9e90ef8-5c77-4c4f-9214-eeecbc890cc5\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.9267026316498884,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"39ee22e9-1be8-44cf-bf2c-9334b4741a57\",\"Layer\":0,\"NodeIdentifier\":0},{\"Id\":\"45ea4559-3f38-48bd-b337-e3105225573e\",\"Layer\":0,\"NodeIdentifier\":1}],\"OutputNodes\":[{\"Id\":\"0051c8eb-ef42-4852-bc57-b2897cc9d73b\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"cous\",\"Generation\":2},{\"Id\":\"cc95b2f0-b498-4291-b9fd-d6d1654b7459\",\"ConnectionGenes\":[{\"Id\":\"9c738b6b-7efd-480d-e6a6-08d7a435cc56\",\"OrganismId\":\"cc95b2f0-b498-4291-b9fd-d6d1654b7459\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":0.8646167163572352,\"Enabled\":true},{\"Id\":\"3702122f-1df3-4761-e6a7-08d7a435cc56\",\"OrganismId\":\"cc95b2f0-b498-4291-b9fd-d6d1654b7459\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":0.8874416885839037,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"1a4c974f-f4f3-4bab-bcba-675e0c222d44\",\"Layer\":0,\"NodeIdentifier\":1},{\"Id\":\"c028f4b5-3475-46a9-bf6d-d4ba999a9728\",\"Layer\":0,\"NodeIdentifier\":0}],\"OutputNodes\":[{\"Id\":\"985cc945-e4bd-44f1-b45c-bc14cb0af9f4\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"gan\",\"Generation\":2},{\"Id\":\"9704e91d-ae1e-4ff7-9a02-c67d4f86182f\",\"ConnectionGenes\":[{\"Id\":\"1c65a50e-2c21-439e-e6ac-08d7a435cc56\",\"OrganismId\":\"9704e91d-ae1e-4ff7-9a02-c67d4f86182f\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":2,\"Weight\":-0.8779935286743537,\"Enabled\":false},{\"Id\":\"b9e266da-932b-4eeb-e6ad-08d7a435cc56\",\"OrganismId\":\"9704e91d-ae1e-4ff7-9a02-c67d4f86182f\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":0.3642462377269968,\"Enabled\":true},{\"Id\":\"7f27e42d-778e-4fba-e6ae-08d7a435cc56\",\"OrganismId\":\"9704e91d-ae1e-4ff7-9a02-c67d4f86182f\",\"InNodeIdentifier\":1,\"OutNodeIdentifier\":3,\"Weight\":1,\"Enabled\":true},{\"Id\":\"68d79c3c-cf40-438b-e6af-08d7a435cc56\",\"OrganismId\":\"9704e91d-ae1e-4ff7-9a02-c67d4f86182f\",\"InNodeIdentifier\":3,\"OutNodeIdentifier\":2,\"Weight\":-0.8672354359027163,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"cccf3da0-cf42-4fb6-9749-660eb011359a\",\"Layer\":0,\"NodeIdentifier\":1},{\"Id\":\"06b715ea-adaa-40e3-8e7a-7f1dbecd069b\",\"Layer\":0,\"NodeIdentifier\":0}],\"OutputNodes\":[{\"Id\":\"0a7901ff-2a01-44be-adc9-8ffca05d8000\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"hyz\",\"Generation\":2},{\"Id\":\"cb6d22f5-4d6c-4add-81ad-d7f518634fcc\",\"ConnectionGenes\":[{\"Id\":\"7474d31c-073d-4b39-e69b-08d7a435cc56\",\"OrganismId\":\"cb6d22f5-4d6c-4add-81ad-d7f518634fcc\",\"InNodeIdentifier\":0,\"OutNodeIdentifier\":2,\"Weight\":-0.36319920670390093,\"Enabled\":true}],\"InputNodes\":[{\"Id\":\"cc2f4aaa-79f1-40f5-bef2-4f8788269440\",\"Layer\":0,\"NodeIdentifier\":0},{\"Id\":\"3c2f4a2d-767a-4807-af5f-a40a3104dc64\",\"Layer\":0,\"NodeIdentifier\":1}],\"OutputNodes\":[{\"Id\":\"1b106372-4f1a-4200-9ada-4a3c5bc39a25\",\"Layer\":0,\"NodeIdentifier\":2}],\"Score\":0,\"Name\":\"piebacocauv\",\"Generation\":2}],\"Id\":\"33236a35-9ad4-46cb-aa07-8d68aef87fc4\",\"RequestId\":\"b873fb68-e983-47c8-a846-83b65846afb2\",\"DateTime\":\"2020-01-29T19:17:13.1786668Z\",\"Message\":\"The requested amount of organisms are not all available. The training room is close to a new generation.\",\"Success\":true}";

        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();
        JsonArray organisms = object.getAsJsonArray("Organisms");

        Gson gson = new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        for(int i = 0; i < organisms.size(); i++) {
            System.out.println(organisms.get(i).toString());
            Organism organism = gson.fromJson(organisms.get(i), Organism.class);
            organism.initialize();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/loadOrganismTest.csv")
    void parseFromJsonEvaluate(String json, double inA, double inB, double inC, double expected) {
        Organism organism = new JsonSerializer().deserialize(json.getBytes(StandardCharsets.UTF_8), Organism.class);
        organism.initialize();

        Assertions.assertEquals(expected, organism.evaluate(new double[]{inA, inB, inC})[0], 0.0000000001);
    }
}