package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public class EscapeRoomPlatform implements EscapeRoomAdminAPI, EscapeRoomPortalAPI {
    private Team[] teams;
    private EscapeRoom[] rooms;
    private final int maxCapacity;
    private int crrRoomsCount = 0;

    public EscapeRoomPlatform(Team[] teams, int maxCapacity) {
        this.teams = teams;
        this.maxCapacity = maxCapacity;
        this.rooms = new EscapeRoom[maxCapacity];
    }

    @Override
    public void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException, PlatformCapacityExceededException {
        if (room == null) {
            throw new IllegalArgumentException("room should not be null");
        }

        if (crrRoomsCount + 1 > maxCapacity) {
            throw new PlatformCapacityExceededException("max capacity already reached!");
        }

        if (roomExists(room)) {
            throw new RoomAlreadyExistsException("room already exists");
        }

        for (int i = 0; i < maxCapacity; i++) {
            if (rooms[i] == null) {
                rooms[i] = room;
                crrRoomsCount++;
                break;
            }
        }
    }

    @Override
    public void removeEscapeRoom(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.isBlank()) {
            throw new IllegalArgumentException("roomName should be a valid name of a room");
        }

        for (int i = 0; i < maxCapacity; i++) {
            if (rooms[i] != null && rooms[i].getName().equals(roomName)) {
                rooms[i] = null;
                crrRoomsCount--;
                return;
            }
        }

        throw new RoomNotFoundException("cannot find a room with " + roomName + " name");
    }

    @Override
    public EscapeRoom[] getAllEscapeRooms() {
        EscapeRoom[] crrRooms = new EscapeRoom[crrRoomsCount];

        int roomIndex = 0;

        for (int i = 0; i < maxCapacity; i++) {
            if (rooms[i] != null) {
                crrRooms[roomIndex] = rooms[i];
                roomIndex++;
            }
        }

        return crrRooms;
    }

    @Override
    public void registerAchievement(String roomName, String teamName, int escapeTime) throws RoomNotFoundException, TeamNotFoundException {
        EscapeRoom room = getEscapeRoomByName(roomName);
        Team team = getTeamByName(teamName);

        if (escapeTime <= 0 || escapeTime > room.getMaxTimeToEscape()) {
            throw new IllegalArgumentException("Escape time should be in the range (0, maxTimeToEscape) for this room");
        }

        int bonus = 0;
        double escapeSpeed = escapeTime / (double) room.getMaxTimeToEscape();
        if (escapeSpeed <= 0.5) {
            bonus = 2;
        } else if (escapeSpeed <= 0.75) {
            bonus = 1;
        }

        team.updateRating(room.getDifficulty().getRank() + bonus);
    }

    @Override
    public EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException {
        if (roomName == null || roomName.isEmpty() || roomName.isBlank()) {
            throw new IllegalArgumentException("Room name cannot be null, empty or blank");
        }

        for (int i = 0; i < maxCapacity; i++) {
            if (rooms[i] != null && rooms[i].getName().equals(roomName)) {
                return rooms[i];
            }
        }

        throw new RoomNotFoundException("No such room with name " + roomName);
    }

    @Override
    public void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException {
        if (review == null) {
            throw new IllegalArgumentException("review cannot be null");
        }

        EscapeRoom room = getEscapeRoomByName(roomName);
        room.addReview(review);
    }

    @Override
    public Review[] getReviews(String roomName) throws RoomNotFoundException {
        EscapeRoom room = getEscapeRoomByName(roomName);

        return room.getReviews();
    }

    @Override
    public Team getTopTeamByRating() {
        Team topTeam = null;
        double topTeamRating = 0.0;

        for (Team t : teams) {
            if (t != null && t.getRating() > topTeamRating) {
                topTeam = t;
                topTeamRating = t.getRating();
            }
        }

        return topTeam;
    }

    private boolean roomExists(EscapeRoom room) {
        for (EscapeRoom r : rooms) {
            if (r != null && r.equals(room)) {
                return true;
            }
        }

        return false;
    }

    private Team getTeamByName(String name) throws TeamNotFoundException {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null, empty or blank");
        }

        for (Team t : teams) {
            if (t != null && t.getName().equals(name)) {
                return t;
            }
        }

        throw new TeamNotFoundException("Team name: " + name);
    }
}
