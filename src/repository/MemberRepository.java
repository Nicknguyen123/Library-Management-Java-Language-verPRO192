package repository;

import model.Member;
import model.PremiumMember;
import model.RegularMember;
import service.MemberService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MemberRepository {
    private static final String FOLDER = "data";
    private static final String FILE_PATH = "data/Member.txt";

    public void saveOneMember(Member member) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            String dataLine = convertObjectToString(member);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("❌ Failed to save Member!");
        }
    }

    public void saveAllMember(List<Member> memberList) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            for (Member member : memberList) {
                String dataLine = convertObjectToString(member);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Failed to save Member!");
        }
    }

    public void loadData(MemberService memberService) {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        try (
                FileReader fileReader = new FileReader(FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {

            String dataLine;
            while ((dataLine = bufferedReader.readLine()) != null) {
                Member member = convertStringToObject(dataLine);
                memberService.addMemberFromFile(member);
            }


        } catch (IOException e) {
            System.out.println("⚠️ Warning: Failed to load Member data!");
        }
    }

    private String convertObjectToString(Member member) {
        return member.getId() + '|' + member.getName() + '|' + member.getPhone() + '|' + member.getEmail()
                + '|' + member.getCurrentBorrowedCount() + '|' + member.getTotalBorrowing();
    }

    private Member convertStringToObject(String dataLine) {
        String[] source = dataLine.split("\\|");

        String id = source[0];
        String name = source[1];
        String phone = source[2];
        String email = source[3];
        int curBorrowedCount = Integer.parseInt(source[4]);
        int totalBorrowing = Integer.parseInt(source[5]);

        if (id.startsWith("PRE")) {
            Member member = new PremiumMember(id, name, phone, email, curBorrowedCount, totalBorrowing);
            return member;
        } else {
            Member member = new RegularMember(id, name, phone, email, curBorrowedCount, totalBorrowing);
            return member;
        }

    }
}