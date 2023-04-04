package org.example;

import bg.sofia.uni.fmi.mjt.netflix.Content;
import bg.sofia.uni.fmi.mjt.netflix.ContentType;
import bg.sofia.uni.fmi.mjt.netflix.NetflixRecommender;

import java.io.FileNotFoundException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        NetflixRecommender nr = new NetflixRecommender();

        System.out.println(nr.getContentByKeywords("mentally", "unstable", "driver", "night-time").isEmpty());
    }
}