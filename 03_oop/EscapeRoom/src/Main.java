import bg.sofia.uni.fmi.mjt.escaperoom.EscapeRoomPlatform;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Difficulty;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Theme;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;
import bg.sofia.uni.fmi.mjt.escaperoom.team.TeamMember;

import java.time.LocalDateTime;
import java.time.Month;

public class Main {
    public static void main(String[] args) throws RoomAlreadyExistsException, RoomNotFoundException {
        TeamMember member1 = new TeamMember("John", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member2 = new TeamMember("Peter", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member3 = new TeamMember("Gosho", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member4 = new TeamMember("Martin", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member5 = new TeamMember("Jimmy", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member6 = new TeamMember("Fo", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member7 = new TeamMember("Mitko", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member8 = new TeamMember("Niki", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member9 = new TeamMember("Ronaldo", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));
        TeamMember member10 = new TeamMember("Messi", LocalDateTime.of(2000, Month.APRIL, 4, 20, 20));


        Team team1 = new Team("Footballers", new TeamMember[]{ member7, member8, member9, member10 });
        Team team2 = new Team("Beasts", new TeamMember[]{ member1, member2, member3} );
        Team team3 = new Team("Players", new TeamMember[]{ member4, member5, member6 });

        EscapeRoom escapeRoom1 = new EscapeRoom(
                "Horror",
                Theme.HORROR,
                Difficulty.HARD,
                60,
                12,
                4);
        EscapeRoom escapeRoom2 = new EscapeRoom(
                "Star Wars",
                Theme.SCIFI,
                Difficulty.MEDIUM,
                40,
                15,
                3);
        EscapeRoom escapeRoom3 = new EscapeRoom(
                "WoW",
                Theme.FANTASY,
                Difficulty.EASY,
                20,
                10,
                2);
        EscapeRoom escapeRoom4 = new EscapeRoom(
                "Apple Garden",
                Theme.MYSTERY,
                Difficulty.EXTREME,
                120,
                25,
                3);
        EscapeRoom escapeRoom5 = new EscapeRoom(
                "Final Battle",
                Theme.FANTASY,
                Difficulty.EXTREME,
                60,
                15,
                2);

        EscapeRoomPlatform escapeRoomPlatform = new EscapeRoomPlatform(new Team[]{ team1, team2, team3 }, 4);

        try {
            escapeRoomPlatform.getEscapeRoomByName("");
        } catch (IllegalArgumentException | RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            escapeRoomPlatform.addEscapeRoom(escapeRoom1);
            escapeRoomPlatform.addEscapeRoom(escapeRoom2);
            escapeRoomPlatform.addEscapeRoom(escapeRoom3);
            escapeRoomPlatform.addEscapeRoom(escapeRoom4);
            escapeRoomPlatform.addEscapeRoom(escapeRoom5);
        } catch (RoomAlreadyExistsException | PlatformCapacityExceededException e) {
            System.out.println(e.getMessage());
        }

        for (EscapeRoom escRoom : escapeRoomPlatform.getAllEscapeRooms()) {
            System.out.println(escRoom.getName());
        }

        try {
            escapeRoomPlatform.removeEscapeRoom("Apple Garden");
        } catch (IllegalArgumentException | RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }

        for (EscapeRoom escRoom : escapeRoomPlatform.getAllEscapeRooms()) {
            System.out.println(escRoom.getName());
        }
    }
}