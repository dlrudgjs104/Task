package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApacheCommonsCLi {
    private String[] args;
    private DataHandler dataHandler;
    private String typeValue;
    private String idValue;
    private String nameValue;
    private int energyValue;
    private int attackPowerValue;
    private int defenceValue;
    private int movingSpeedValue;
    private int attackSpeedValue;
    private String historyValue;
    private String dbFileValue;

    private JSONArray users = new JSONArray();
    private JSONArray units = new JSONArray();
    private JSONArray modifies = new JSONArray();

    private ModifyLog modifyLog = new ModifyLog();

    public ApacheCommonsCLi(String[] args) {
        this.args = args;
        dataHandler = new DataHandler("./recoder.json");

        // 기존 파일의 User, Unit modify내용 가져오기
        try {
            JSONArray userArray = (JSONArray) dataHandler.get("User");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject object = userArray.getJSONObject(i);
                users.put(object);
            }

            JSONArray unitArray = (JSONArray) dataHandler.get("Unit");
            for (int i = 0; i < unitArray.length(); i++) {
                JSONObject object = unitArray.getJSONObject(i);
                units.put(object);
            }

            JSONArray modifyArray = (JSONArray) dataHandler.get("Log");
            for (int i = 0; i < modifyArray.length(); i++) {
                JSONObject object = modifyArray.getJSONObject(i);
                modifies.put(object);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cliStart() {
        Options options = new Options();

        Option add = Option.builder("a")
                .longOpt("add")
                .desc("add")
                .build();

        Option type = Option.builder("t")
                .longOpt("type")
                .hasArg()
                .desc("type")
                .build();

        Option id = Option.builder("i")
                .longOpt("id")
                .hasArg()
                .desc("id")
                .build();

        Option name = Option.builder("n")
                .longOpt("name")
                .hasArg()
                .desc("name")
                .build();

        Option list = Option.builder("l")
                .longOpt("list")
                .desc("list")
                .build();

        Option count = Option.builder("c")
                .longOpt("count")
                .desc("count")
                .build();

        Option win = Option.builder("W")
                .longOpt("Win")
                .desc("win")
                .build();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("help")
                .build();

        Option energy = Option.builder("e")
                .longOpt("energy")
                .hasArg()
                .desc("energy")
                .build();

        Option attack = Option.builder("aa")
                .longOpt("attack")
                .hasArg()
                .desc("attack")
                .build();

        Option defence = Option.builder("d")
                .longOpt("defence")
                .hasArg()
                .desc("id")
                .build();

        Option movingSpeed = Option.builder("m")
                .longOpt("moving-speed")
                .hasArg()
                .desc("moving speed")
                .build();

        Option attackSpeed = Option.builder("A")
                .longOpt("attack-speed")
                .hasArg()
                .desc("attack speed")
                .build();

        Option history = Option.builder("L")
                .longOpt("history")
                .desc("history")
                .build();

        Option dbFile = Option.builder("f")
                .longOpt("db-file")
                .hasArg()
                .desc("db file")
                .build();

        options.addOption(add);
        options.addOption(type);
        options.addOption(id);
        options.addOption(name);
        options.addOption(list);
        options.addOption(count);
        options.addOption(win);
        options.addOption(help);
        options.addOption(energy);
        options.addOption(attack);
        options.addOption(defence);
        options.addOption(movingSpeed);
        options.addOption(attackSpeed);
        options.addOption(history);
        options.addOption(dbFile);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption(add.getOpt())) {
                System.out.println("add");
            }

            if (cmd.hasOption(type.getOpt())) {
                typeValue = cmd.getOptionValue(type.getOpt());
                System.out.println("type: " + typeValue);
            }

            if (cmd.hasOption(id.getOpt())) {
                idValue = cmd.getOptionValue(id.getOpt());
                System.out.println("id: " + idValue);
            }

            if (cmd.hasOption(name.getOpt())) {
                nameValue = cmd.getOptionValue(name.getOpt());
                System.out.println("name: " + nameValue);
            }

            if (cmd.hasOption(list.getOpt())) {
                System.out.println("list");
            }

            if (cmd.hasOption(count.getOpt())) {
                System.out.println("count");
            }

            if (cmd.hasOption(win.getOpt())) {
                System.out.println("win");
            }

            if (cmd.hasOption(help.getOpt())) {
                System.out.println("help");
            }

            if (cmd.hasOption(energy.getOpt())) {
                energyValue = Integer.parseInt(cmd.getOptionValue(energy.getOpt()));
                System.out.println("energy: " + energyValue);
            }

            if (cmd.hasOption(attack.getOpt())) {
                attackPowerValue = Integer.parseInt(cmd.getOptionValue(attack.getOpt()));
                System.out.println("attackPower: " + attackPowerValue);
            }

            if (cmd.hasOption(defence.getOpt())) {
                defenceValue = Integer.parseInt(cmd.getOptionValue(defence.getOpt()));
                System.out.println("defence: " + defenceValue);
            }

            if (cmd.hasOption(movingSpeed.getOpt())) {
                movingSpeedValue = Integer.parseInt(cmd.getOptionValue(movingSpeed.getOpt()));
                System.out.println("movingSpeed: " + movingSpeedValue);
            }

            if (cmd.hasOption(attackSpeed.getOpt())) {
                attackSpeedValue = Integer.parseInt(cmd.getOptionValue(attackSpeed.getOpt()));
                System.out.println("attackSpeed: " + attackSpeedValue);
            }

            if (cmd.hasOption(history.getOpt())) {
                historyValue = cmd.getOptionValue(history.getOpt());
                System.out.println("history: " + historyValue);
            }

            if (cmd.hasOption(dbFile.getOpt())) {
                dbFileValue = cmd.getOptionValue(dbFile.getOpt());
                System.out.println("dbFile: " + dbFileValue);
            }

            if (cmd.hasOption(add.getOpt())) {
                if (typeValue.equals("user")){
                    userAdd();
                }
                else{
                    unitAdd();
                }     
            }

            if (cmd.hasOption(list.getOpt())){
                if (typeValue.equals("user")){
                    userList();
                }
                else{
                    unitList();
                }
            }

        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    public void userAdd() {
        User user = new User(idValue, nameValue);
        
        users.put(user.getUserObject());
        dataHandler.put("User", users);

        // 변경된 내용 저장
        modifies.put(modifyLog.getModify(user.getUserObject()));
        dataHandler.put("Log", modifies);

        dataHandler.close();
    }

    public void unitAdd() {
        Unit unit = new Unit(idValue, nameValue, energyValue, attackPowerValue, defenceValue, movingSpeedValue, attackSpeedValue);
 
        units.put(unit.getUnitObject());
        dataHandler.put("Unit", units);

        // 변경된 내용 저장
        modifies.put(modifyLog.getModify(unit.getUnitObject()));
        dataHandler.put("Log", modifies);

        dataHandler.close();
    }

    public void userList(){
        System.out.println(String.format("%5s %5s", "ID", "NAME"));

        // 각 사용자의 ID를 출력합니다.
        for (int i = 0; i < users.length(); i++) {
            JSONObject userObject = users.getJSONObject(i);
            String id = userObject.getString("ID");
            String nikeName = userObject.getString("NIKENAME");
            System.out.println(String.format("%5s %5s", id, nikeName));
        }

    }

    public void unitList(){
        // 각 사용자의 ID를 출력합니다.
        System.out.println(String.format("%10s %10s %10s %10s %10s %10s %10s", "UNITID", "MODEL", "HP", "ATTACKPOWER", "DEFENSE", "ATTACKSPEED", "MOVINGSPEED"));

        for (int i = 0; i < units.length(); i++) {
            JSONObject unitObject = units.getJSONObject(i);
            String unitId = unitObject.getString("UNITID");
            String model = unitObject.getString("MODEL");
            int hp = unitObject.getInt("HP");
            int attackPower = unitObject.getInt("ATTACKPOWER");
            int defense = unitObject.getInt("DEFENSE");
            int movingSpeed = unitObject.getInt("MOVINGSPEED");
            int attackSpeed = unitObject.getInt("ATTACKSPEED");
            System.out.println(String.format("%10s %10s %10d %10d %10d %10d %10d", unitId, model, hp, attackPower, defense, attackSpeed, movingSpeed));
        }
    }

}