package funappsstudios.com.testlibrasparayoutubeenetflix.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import funappsstudios.com.testlibrasparayoutubeenetflix.model.data.Caption;

/**
 * Created by Leandro on 12/06/2017.
 */

public class XmlToCaptionsConverter {

    private XmlToCaptionsConverter() {}


    public static List<Caption> convert(String xml) {

        // remove head
        String step1 = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\" ?><transcript>", "");
        // remove line breaks
        String step2 = step1.replace("\n", "");
        // remove end tags
        String step3 = step2.replace("</text>", "");


        // one element for each <text></text>
        String[] test = new String(step3).split("<text ");


        List<Caption> captions = new ArrayList<>(test.length );


        for (int i = 1; i < test.length; i++) {
            Caption cap = new Caption();

            //System.out.println("a: " + test[i]);

            // get start
            int index1 = StringUtils.ordinalIndexOf(test[i], "\"", 1);
            int index2 = StringUtils.ordinalIndexOf(test[i], "\"", 2);

            String start = test[i].substring(index1 + 1, index2);
            cap.setStart(Float.valueOf(start));

            // get duration
            int index3 = StringUtils.ordinalIndexOf(test[i], "\"", 3);
            int index4 = StringUtils.ordinalIndexOf(test[i], "\"", 4);

            String duration = test[i].substring(index3 + 1, index4);
            cap.setStart(Float.valueOf(duration));

            // get text
            String text = test[i].substring(test[i].indexOf(">") + 1);
            cap.setText(text);

            // add caption
            captions.add(cap);
        }


        return captions;
    }



}
