package com.company;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class WeaponData {
    String name = "";
    HashMap<String, Object> parameters = new HashMap<String, Object>();      // keys & values

    WeaponData(JSONObject json, String weaponName){
        this.name = weaponName;
        getParameters(json);
        System.out.println(this.parameters.toString());
    }

    void getParameters(JSONObject json) {
        Set<String> keys = json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject(this.name).keySet();
        keys.forEach((key)->{
            parameters.put(key, json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject(this.name).get(key));
        });
    }

    Object getValue(String key){
        return parameters.get(key);
    }
}

class WeaponsData extends ArrayList<WeaponData> {
    WeaponsData() {
        super();

/*        keys.forEach((key)->{
            parameters.put(key, json.getJSONObject("weapons").getJSONObject(this.name).get(key));
        });*/
    }
}

public class Weapon {
    String name;
    double weight;  // 1-10
    int range;  // 1 (short), 2 (wider), 3 (projectile)
    double damage;  // 1-10;
    double inspiringConfidence;
    boolean needsAmmo;
    int ammo;

    public Weapon(String name, double weight, int range, double damage, boolean needsAmmo, int ammo){
        this.name = name;
        this.weight = weight;
        this.range = range;
        this.damage = damage;
        this.needsAmmo = needsAmmo;
        this.ammo = ammo;
        inspiringConfidence = damage/weight;
        if (needsAmmo) inspiringConfidence = (inspiringConfidence * ammo)/ammo;
    }
    String loadFileToString(){
        String filePath = "C:\\Users\\nsx\\IdeaProjects\\WannabeDeathmatch\\res\\weapons.json"; //"../../res/weapons.json";
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    void walkJson(JSONObject json){
       Set<String> keyset = json.keySet();
       keyset.forEach((s) ->{
           System.out.println("walk->" + s);
           JSONObject nextJson = (JSONObject) json.get(s);
           walkJson(nextJson);
       });
    }
/*    void walkJson(ArrayList arrayList, JSONObject json){
        //System.out.println("walk:" + arrayList.toString());
        JSONArray jsonArray = json.getJSONObject("weapons").names();
        arrayList.forEach((list) -> {
            System.out.println("walk:" + list);
            json.getJSONObject(json.names().toList();
            walkJson((ArrayList) list);
        });
    }
*/

    void walkWeaponDefinitionValues(Set<String> setOfWeaponDefinitionValues, JSONObject json, String weaponName, String weaponParameter) { // <-- weight, range, ...
        //setOfWeaponDefinitionValues.forEach((s) -> {                                                                                       // --> values of weight, ...
            System.out.println("Weapon-name: " + weaponName + "\tParameter-name: " + weaponParameter + "\tValue: " +
                    json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject(weaponName).get(weaponParameter).toString());
        //});
    }

    void walkWeaponDefinitions(Set<String> setOfWeaponDefinitions, JSONObject json, String weaponName){ // <-- Knife, Ace, ...
        setOfWeaponDefinitions.forEach((s) -> {                                                         // --> weight, range, ...
            //System.out.println("D: " + s);
            walkWeaponDefinitionValues(setOfWeaponDefinitions, json, weaponName, s);
        });
    }

    void walkWeapons(Set<String> setOfWeapons, JSONObject json){        // --> ín walker der mit JsonObject arbeitet umwandeln
        setOfWeapons.forEach((s) -> {
            //System.out.println("W: " + s);
            Set<String> allWeaponsDefinitions = json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject(s).keySet();
            walkWeaponDefinitions(allWeaponsDefinitions, json, s);
        });
    }

    void loadJson() {
        JSONObject json = new JSONObject(loadFileToString());
        //System.out.println(loadFileToString());
        System.out.println(json.names().toString());

        //ArrayList arrayList = new ArrayList();
        System.out.println(json.getJSONObject("weapons").names().toList());
        System.out.println(json.toString());
        System.out.println(json.get("weapons").toString());
        JSONObject weapons = (JSONObject) json.get("weapons");
        JSONObject definitions = (JSONObject) weapons.get("definitions");
        System.out.println("Definitionen: " + definitions.toString());
        JSONObject knife = (JSONObject) definitions.get("Knife");
        System.out.println("Knife: " + knife.toString());
        int damage = knife.getInt("damage");
        System.out.println("Damage: " + damage);

        Set<String> knifes = json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject("Knife").keySet();
        System.out.println("alle Daten zu Knife: " + knifes.toString());

        Set<String> allWeapons = json.getJSONObject("weapons").getJSONObject("definitions").keySet();
        walkWeapons(allWeapons, json);


        JSONObject knifebla =  json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject("Knife");
        String key = knifebla.keys().next(); //parameter name
        String value = knifebla.get(key).toString();       //parameter value
        System.out.println("HIER: " + key + "\t" + value);


        //Set<String> allWeaponsDefinitions = json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject("Knife").keySet();
        //walkWeaponDefinitions(allWeaponsDefinitions, json, "");

        int realdamage = json.getJSONObject("weapons").getJSONObject("definitions").getJSONObject("Knife").getInt("damage");
        System.out.println("JSNOOBJECT int: " + realdamage);

        WeaponData messer = new WeaponData(json, "Knife");
        System.out.println(messer.name + " - Damage:" + messer.getValue("damage"));

        WeaponsData waffen = new WeaponsData();
        waffen.add(messer);
        waffen.add(messer);
        waffen.add(messer);

        //arrayList= (ArrayList) json.getJSONObject("weapons").names().toList();
        //System.out.println(arrayList.toString());
        //walkJson(arrayList);
        //walkJson((JSONObject) json.get("weapons"));
    }

    public String getSpecs(){
        String weaponClass;
        if (range==1) weaponClass =      "close-combat weapon";
        else if (range==2) weaponClass = "thrusting weapon   ";
        else if (range==3) weaponClass = "projectile weapon  ";
        else weaponClass = "multiuse";
        String ammoStatus = "";
        if (needsAmmo) ammoStatus += "\tAMMO: " + ammo;
        return name + "\t\tWEIGHT: " + weight + "\tTYPE: " + weaponClass + "\tDAMAGE: " + damage + ammoStatus;
    }
}
