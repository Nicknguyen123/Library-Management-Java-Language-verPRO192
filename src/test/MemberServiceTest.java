package test;

import model.Member;
import model.PremiumMember;
import model.RegularMember;
import repository.MemberRepository;
import service.MemberService;
import sun.java2d.pipe.SpanClipRenderer;

public class MemberServiceTest {
    private static int totalTest;
    private static int passedTest;

    public static void main(String[] args) {
        testAddMemberSuccessfully();
        testDuplicateIdWhenAddMember();
        testDuplicatePhoneWhenAddMember();
        testDuplicateEmailWhenAddMember();
        testDeleteMemberSuccessfully();
        testDeleteMemberButNotFoundId();
        testUpdateNameSuccessfully();
        testUpdatePhoneSuccessfully();
        testDuplicatePhoneWhenUpdatePhone();
        testUpdateEmailSuccessfully();
        testDuplicateEmailWhenUpdateEmail();

        System.out.println();
        printStatistic();
    }

    private static void printStatistic() {
        System.out.println("========================= TEST SUMMARY =========================");
        System.out.println("Total Test     : " + totalTest);
        System.out.println("Passed Test    : " + passedTest);
        System.out.println("Failed Test    : " + (totalTest - passedTest));
    }

    private static void checkTrueFalse(String testName, boolean condition) {
        totalTest++;
        if (condition) {
            System.out.println("PASS - " + testName);
            passedTest++;
        } else {
            System.out.println("FAIL - " + testName);
        }
    }

    private static void checkThrowException(String testName, Runnable action) {
        totalTest++;
        try {

            action.run();
            System.out.printf("FAIL - %s | Expected exception but nothing was thrown\n", testName);

        } catch (IllegalArgumentException e) {

            System.out.printf("PASS - %s | Exception: %s\n", testName, e.getMessage());
            passedTest++;

        } catch (Exception e) {
            System.out.printf("FAIL - %s | Wrong exception: %s\n", testName, e.getClass().getSimpleName());
        }
    }

    private static Member createMember(String id) {
        return new PremiumMember(id, "Nguyễn Khôi Nguyên", "0984083792",
                "khoinguyenperfect@gmail.com");
    }

    private static void testAddMemberSuccessfully() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);

        Member temp = memberService.findMemberById("PRE123");
        checkTrueFalse("testAddMemberSuccessfully", temp != null);
    }

    private static void testDuplicateIdWhenAddMember() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        Member member1 = new PremiumMember("PRE123", "bla bla", "0985672345", "lekha@gmail.com");
        memberService.addMemberVerTest(member);

        Runnable action = () -> memberService.addMemberVerTest(member1);
        checkThrowException("testDuplicateIdWhenAddMember", action);
    }

    private static void testDuplicatePhoneWhenAddMember() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        Member member1 = new RegularMember("REG780", "le kha huy", "0984083792",
                "lekhahuy@gmail.com");
        memberService.addMemberVerTest(member);

        Runnable action = () -> memberService.addMemberVerTest(member1);
        checkThrowException("testDuplicatePhoneWhenAddMember", action);
    }

    private static void testDuplicateEmailWhenAddMember() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        Member member1 = new RegularMember("REG780", "le kha huy", "0932458910",
                "khoinguyenperfect@gmail.com");
        memberService.addMemberVerTest(member);

        Runnable action = () -> memberService.addMemberVerTest(member1);
        checkThrowException("testDuplicateEmailWhenAddMember", action);
    }

    private static void testDeleteMemberSuccessfully() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);

        memberService.deleteMemberVerTest("PRE123");
        Member temp = memberService.findMemberById("PRE123");
        checkTrueFalse("testDeleteMemberSuccessfully", temp == null);
    }

    private static void testDeleteMemberButNotFoundId() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);

        Runnable action = () -> memberService.deleteMemberVerTest("REG780");
        checkThrowException("testDeleteMemberSuccessfully", action);
    }

    private static void testUpdateNameSuccessfully() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);
        memberService.updateNameVerTest(member, "Lê Khả Huy");

        checkTrueFalse("testUpdateNameSuccessfully", member.getName().equals("Lê Khả Huy"));
    }

    private static void testUpdatePhoneSuccessfully() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);
        memberService.updatePhone(member, "0983845479");

        checkTrueFalse("testUpdatePhoneSuccessfully", member.getPhone().equals("0983845479"));
    }

    private static void testDuplicatePhoneWhenUpdatePhone() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);

        Runnable action = () -> memberService.updatePhoneVerTest(member, "0984083792");
        checkThrowException("testDuplicatePhoneWhenUpdatePhone", action);
    }

    private static void testUpdateEmailSuccessfully() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);
        memberService.updateEmailVerTest(member, "lekhahuy@gmail.com");

        checkTrueFalse("testUpdateEmailSuccessfully", member.getEmail().equals("lekhahuy@gmail.com"));
    }

    private static void testDuplicateEmailWhenUpdateEmail() {
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        Member member = createMember("PRE123");
        memberService.addMemberVerTest(member);

        Runnable action = () -> memberService.updateEmail(member, "khoinguyenperfect@gmail.com");
        checkThrowException("testDuplicateEmailWhenUpdateEmail", action);
    }
}
