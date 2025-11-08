package me.Testing;

import me.API.Net;
import me.API.Params;
import me.API.Info;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(Arrays.toString(
                Info.info.getRawSimilarTracks(Info.info.getSeedFromRequest(Net.netty.sendGETForFindRequest("dvrst - dream space")), new Params(25)
        )));
    }
}
