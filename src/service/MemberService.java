package service;

import com.sun.org.apache.regexp.internal.RE;
import model.Member;
import repository.MemberRepository;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MemberService {
    private final List<Member> memberList;
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberList = new ArrayList<>();
        this.memberRepository = memberRepository;
    }

    public List<Member> getMemberList() {
        return new ArrayList<>(memberList);
    }

    public void addMember(Member member) {
        checkMemberNull(member);

        String memberId = member.getId();
        if (findMemberById(memberId) != null) {
            throw new IllegalArgumentException("❌ This Member already exists in the system. " +
                    "Duplicate ID: " + memberId);
        }

        if (checkDuplicatePhone(member.getPhone())) {
            throw new IllegalArgumentException("❌ Phone number already registered: " + member.getPhone());
        }

        if (checkDuplicateEmail(member.getEmail())) {
            throw new IllegalArgumentException("❌ Email already registered: " + member.getEmail());
        }

        memberList.add(member);
        memberRepository.saveOneMember(member);
    }

    public void addMemberFromFile(Member member) {
        memberList.add(member);
    }

    public void deleteMember(String memberId) {
        String safeId = Validator.validateBasicString(memberId);

        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getId().equals(safeId)) {
                memberList.remove(i);
                memberRepository.saveAllMember(memberList);
                return;
            }
        }

        throw new IllegalArgumentException("⚠️ Alert: Unable to remove. No member found with the specified ID: "
                + safeId);
    }

    public void updateName(Member member, String newName) {
        checkMemberNull(member);

        String safeName = Validator.validateBasicString(newName);

        member.setName(safeName);
        memberRepository.saveAllMember(memberList);
    }

    public void updatePhone(Member member, String newPhone) {
        checkMemberNull(member);

        String safePhone = Validator.validateBasicString(newPhone);

        if (checkDuplicatePhone(safePhone)) {
            throw new IllegalArgumentException("❌ Phone number already registered: " + member.getPhone());
        }

        member.setPhone(safePhone);
        memberRepository.saveAllMember(memberList);
    }

    public void updateEmail(Member member, String newEmail) {
        checkMemberNull(member);

        String safeEmail = Validator.validateBasicEmail(newEmail);

        if (checkDuplicateEmail(safeEmail)) {
            throw new IllegalArgumentException("❌ Email already registered: " + member.getEmail());
        }

        member.setEmail(safeEmail);
        memberRepository.saveAllMember(memberList);
    }

    public void showAllMember() {
        if (memberList.isEmpty()) {
            System.out.println("⚠️ No members found in the system.");
            return;
        }

        String border = "+----------------------------------------------------------------------------------" +
                "---------------------------------------------------------------------+";

        System.out.println(border);
        System.out.printf("| %-10s | %-12s | %-20s | %-12s | %-25s | %-10s | %-10s | %-12s | %-10s |\n",
                "👑 TYPE", "🆔 ID", "👤 NAME", "📞 PHONE", "📧 EMAIL", "📚 CUR_BOOK", "📈 TOTAL",
                "⚠️ LIMIT", "💸 FINE_RATE");
        System.out.println(border);

        Consumer<Member> memberConsumer = (member) -> member.showMemberInfo();
        memberList.forEach(memberConsumer);
        System.out.println(border);
    }

    public Member findMemberById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (Member member : memberList) {
            if (member.getId().equals(safeId)) {
                return member;
            }
        }

        return null;
    }

    public List<Member> findMemberByName(String name) {
        String safeName = Validator.validateBasicString(name);

        List<Member> foundMember = new ArrayList<>();
        for (Member member : memberList) {
            if (member.getName().contains(safeName)) {
                foundMember.add(member);
            }
        }

        if (foundMember.isEmpty()) {
            return null;
        } else {
            return foundMember;
        }
    }

    public void checkMemberNull(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("❌ Member cannot be null");
        }
    }

    private boolean checkDuplicatePhone(String phoneNumber) {
        String safePhone = Validator.validateBasicString(phoneNumber);

        for (Member member : memberList) {
            if (member.getPhone().equals(safePhone)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDuplicateEmail(String email) {
        String safeEmail = Validator.validateBasicEmail(email);

        for (Member member : memberList) {
            if (member.getEmail().equals(safeEmail)) {
                return true;
            }
        }

        return false;
    }
}