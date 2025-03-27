package dev.mobile.bai2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Schedule {
    private String title;
    private String place;
    private String date;
    private String time;
    private boolean isEnabled = false;

    public Schedule(String title, String room, String date, String time) {
        this.title = title;
        this.place = room;
        this.date = date;
        this.time = time;
    }
}
