package org.example.story;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.management.OperatingSystemMXBean;
import org.example.story.characters.Ground;
import org.example.story.characters.Person;
import org.example.story.characters.Wind;
import org.example.story.enums.DamagesPart;
import org.example.story.enums.States;
import org.example.story.info.Group;
import org.example.story.nature.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class StoryTest {

    private PrintStream originalOut;
    private final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private ByteArrayOutputStream getOutputStream() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(500);
        if (originalOut != null)
            System.setOut(originalOut);
        System.out.printf("CPU load: %.3f%n", osBean.getProcessCpuLoad() * 100);
    }

    @ParameterizedTest
    @CsvSource({
            "30, 20, 80, горло",
            "100, 75, 20, уши",
            "45, 46, 46, глаза",
            "50, 50, 50, уши"
    })
    public void findMostDamagedPartTest(int d1, int d2, int d3, String part) {
        getOutputStream();
        Person person = new Person("p1", States.NORMAL);
        Wind wind = new Wind();
        wind.negativeAction(person, DamagesPart.EARS, d1);
        wind.negativeAction(person, DamagesPart.EYES, d2);
        wind.negativeAction(person, DamagesPart.THROAT, d3);
        DamagesPart damagesPart = person.findMostDamagedPart();

        assertEquals(part, damagesPart.getDescription());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 100, 100",
            "0, 0, 0"
    })
    public void findMostDamagedPartTestAllEquals(int d1, int d2, int d3) {
        getOutputStream();
        Person person = new Person("p1", States.NORMAL);
        Wind wind = new Wind();
        wind.negativeAction(person, DamagesPart.EARS, d1);
        wind.negativeAction(person, DamagesPart.EYES, d2);
        wind.negativeAction(person, DamagesPart.THROAT, d3);

        ByteArrayOutputStream outputStream = getOutputStream();
        person.findMostDamagedPart();
        assertEquals("У персонажа p1 всё одинаково повреждено.\r\n", outputStream.toString());
    }

    @Test
    public void findMostDamagedPartTestEmpty() {
        Person person = new Person("p1", States.NORMAL);
        ByteArrayOutputStream outputStream = getOutputStream();
        DamagesPart damagesPart = person.findMostDamagedPart();
        assertEquals("У персонажа p1 нет повреждений.\r\n", outputStream.toString());
        assertNull(damagesPart);
    }

    @Test
    public void describeFloraOfSwampNoFlora() {
        Swamp swamp = new Swamp("Сибирское");
        ByteArrayOutputStream outputStream = getOutputStream();
        swamp.describeFlora();
        assertEquals("""
                Болото Сибирское лишено растительности.\r
                Болото Сибирское покрыто пылью в 0.0 дюйм толщиной.\r
                """, outputStream.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "GRASS, TREES, 2",
            "FLOWERS, GRASS, 10",
            "TREES, FLOWERS, 3.5"
    })
    public void describeFloraOfSwampWithFlora(String f1, String f2, double dust) {
        Swamp swamp = new Swamp("Сибирское");
        FloraItems flora1 = FloraItems.valueOf(f1);
        FloraItems flora2 = FloraItems.valueOf(f2);

        swamp.setFloras(List.of(flora1, flora2));
        swamp.setDustInch(dust);
        ByteArrayOutputStream outputStream = getOutputStream();
        swamp.describeFlora();
        assertEquals(String.format(Locale.ENGLISH, "Болото Сибирское имеет следующую растительность: %s, %s, " +
                        "Болото Сибирское покрыто пылью в %.1f дюйм толщиной.\r\n",
                flora1.getFloraItem(), flora2.getFloraItem(), dust), outputStream.toString());
    }

    @Test
    public void groundIsSimilarToTest() {
        Ground ground = new Ground("Новая");
        ground.setSimilarBiome(new Swamp("Сибирское"));
        ByteArrayOutputStream outputStream = getOutputStream();
        ground.isSimilarTo(false);
        assertEquals("Земля Новая похожа на Болото Сибирское\r\n", outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void getRemoteMemberTest(int member) {
        Group group = new Group("B");
        group.setMembers(List.of(
                new Person("p0"),
                new Person("p1"),
                new Person("p2"),
                new Person("p3")));
        group.setLeader(group.getMembers().get(3));
        group.getMembers().forEach(m -> {
            if (Objects.equals(m.getName(), "p" + member)) {
                m.setX(30);
                m.setY(30);
            } else {
                m.setX(5);
                m.setY(5);
            }
        });

        ByteArrayOutputStream outputStream = getOutputStream();
        Person remotePerson = group.getRemoteMember();
        assertEquals(remotePerson.getName(), "p" + member);
        String expected = String.format("%s шёл в отдалении от группы B.%n", remotePerson.getName());
        assertEquals(expected, outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void getRemoteMemberTestSmallGroup(int count) {
        Group group = new Group("B");
        for (int i = 0; i < count; i++)
            group.getMembers().add(new Person("Vasya"));

        assertThrows(NullPointerException.class, group::getRemoteMember);
    }

    private String groundAttractsBuilder(Ground ground, int a1, int a2, int a3, int threshold) {
        ground.getLandscapes().addAll(Set.of(
                new Spot(Colors.DULLBROWN, a1),
                new Spot(Colors.DULLGRAY, a2),
                new Spot(Colors.DULLBROWN, a3)));

        StringBuilder spots = new StringBuilder();
        for (Landscape landscape : ground.getLandscapes()) {
            if (landscape.getAttraction() >= threshold)
                spots.append(landscape).append(", ");
        }

        return spots.toString();
    }

    @ParameterizedTest
    @CsvSource({
            "50, 10, 40, 30",
            "100, 99, 90, 95"
    })
    public void groundAttractsTest(int a1, int a2, int a3, int threshold) {
        Ground ground = new Ground("Новая");
        String spots = groundAttractsBuilder(ground, a1, a2, a3, threshold);
        ByteArrayOutputStream outputStream = getOutputStream();
        ground.attracts(threshold);
        assertEquals("Земля Новая привлекала следующими пейзажами: " + spots +
                ". Остальной пейзаж был менее интересным\r\n", outputStream.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "50, 10, 40, 5",
            "100, 99, 90, 90",
    })
    public void groundAttractsTestHighThreshold(int a1, int a2, int a3, int threshold) {
        Ground ground = new Ground("Новая");
        String spots = groundAttractsBuilder(ground, a1, a2, a3, threshold);
        ByteArrayOutputStream outputStream = getOutputStream();
        ground.attracts(threshold);
        assertEquals("Земля Новая привлекала следующими пейзажами: " + spots + "\r\n",
                outputStream.toString());
    }

    @Test
    public void groundAttractsTestEmpty() {
        Ground ground = new Ground("Новая");
        ByteArrayOutputStream outputStream = getOutputStream();
        ground.attracts(0);
        assertEquals("Земля Новая не привлекала никакими пейзажами.\r\n", outputStream.toString());
    }
}
