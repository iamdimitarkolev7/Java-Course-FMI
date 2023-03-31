package org.example;

import bg.sofia.uni.fmi.mjt.netflix.ContentType;
import bg.sofia.uni.fmi.mjt.netflix.NetflixRecommender;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        NetflixRecommender nr = new NetflixRecommender();

        System.out.println(nr.getTopNRatedContent(10));
    }
}