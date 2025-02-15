package org.example.story.info;

import lombok.Data;
import org.example.story.characters.Person;

import java.util.*;

@Data
public class GroupInfo {
    private String groupName = "";
    private Person leader;
    private List<Person> members = new LinkedList<>();

    public void setLeader(Person leader) {
        if (members.contains(leader))
            this.leader = leader;
    }

    public Person getRemoteMember() {
        if (members.size() < 2) return null;

        Person remotePerson = members.stream().max(Comparator.comparing(e ->
                Math.sqrt(Math.pow(leader.getX() - e.getX(), 2) + Math.pow(leader.getY() - e.getY(), 2))))
                .orElse(null);
        System.out.println(remotePerson.getName() + " шёл в отдалении от группы" + groupName);
        return remotePerson;
    }
}
