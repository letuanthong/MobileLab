package dev.mobile.bai2.service;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.bai2.model.Schedule;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataGenerator {
    public List<Schedule> getData() {
        List<Schedule> list = new ArrayList<Schedule>();

        list.add(new Schedule("Quản trị hệ thống mạng", "A305", "?", "?"));
        list.add(new Schedule("Cấu trúc dữ liệu và giải thuật", "A407", "?", "?"));
        list.add(new Schedule("Phát triển ứng dụng di động", "A508", "?", "?"));
        list.add(new Schedule("Công nghệ Java", "A603", "?", "?"));

        return list;
    }
}
