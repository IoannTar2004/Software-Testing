package org.example.story.info;

import lombok.Data;
import org.example.story.characters.Person;

import java.util.*;

@Data
public class Group {
    private String groupName;
    private Person leader;
    private List<Person> members = new LinkedList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
        this.groupName = "";
    }

    public void setLeader(Person leader) {
        if (members.contains(leader))
            this.leader = leader;
    }

    public Person getRemoteMember() {
        if (members.size() < 3) {
            throw new NullPointerException("Группа " + groupName + " содержит менее 3 человек.");
        }

        Person remotePerson = members.stream().max(Comparator.comparing(e ->
                Math.sqrt(Math.pow(leader.getX() - e.getX(), 2) + Math.pow(leader.getY() - e.getY(), 2))))
                .orElse(null);
        System.out.printf("%s шёл в отдалении от группы %s.%n", remotePerson.getName(), groupName);
        return remotePerson;
    }
}
