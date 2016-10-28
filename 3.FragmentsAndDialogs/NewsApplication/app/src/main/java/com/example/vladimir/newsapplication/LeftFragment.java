package com.example.vladimir.newsapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LeftFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private ArrayList<Article> mArticles;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_side_fragment,container,false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView) ;

        mArticles = new ArrayList<>();
        addArticles();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new ArticleAdapter(mArticles,(ArticleAdapter.ILeftFragmentHandler)mContext);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void addArticles() {
        mArticles.add(new Article("Dog Adopts Piglets","13-09-2016 15:00","   Meet Linda, recent mother of six pups and now an adoptive mum to a litter of piglets. Her owner explained how this unique scenario came about.\n" +
                "   The mother pig died but had already given birth, so he collected the piglets and brought them to his home where his dog had also had puppies. He put the babies together to see if the dog would accept them.\n" +
                "   On the first day, she was a little hesitant, but she ended up giving the piglets milk. A week on, she would arrive of her own volition to feed the piglets.\n" +
                "   Linda has now become something of a local attraction in the Cuban town of Playa Larga.", R.drawable.img_piglets));

        mArticles.add(new Article("New York’s Hot Dogs","08-09-2016 15:00","   One man said that Nathan’s Hot Dogs is like the Empire State Building or the Brooklyn Bridge. They are all something very typical of the New York City.\n" +
                "   New York’s famous hot dogs have been celebrating 100 years of history and in no ordinary fashion. People made a world record attempt for the longest continuous line of hot dogs by laying out Nathan’s Hot dogs.\n" +
                "   They laid out 1,916 buns in total, which was stretching to an impressive 292 meters. People set the previous record in Tokyo by 34 metres.", R.drawable.img_hotdog));

        mArticles.add(new Article("Chocolate Expert","12-09-2016 07:00","   Taste buds are as important to Cadbury, a world-famous chocolate brand, as legs are to a top footballer. The company has had one of their scientist’s taste buds insured for 1 million pounds.\n" +
                "   The scientist works as part of a 300-strong team of inventors in the innovation kitchen in Birmingham. The company says that her expert taste buds are essential for making sure that all of Cadbury’s new creations meet its highest standards.\n" +
                "   To avoid rendering the insurance void, she needs to not take up sword swallowing, eat fugu fish, or the hottest of chilli peppers. None of these sound more appealing than getting paid to taste chocolate!\n", R.drawable.img_chocolate));

        mArticles.add(new Article("World Scrabble Championship","09-09-2016 07:00","   Scrabble enthusiasts from 30 countries competed at the World Scrabble Championship in Lille, France. People described the final between two Brits as nail-biting.\n" +
                "   There were many high-scoring words, and two of them really stood out from the final – gynaecia and braconid which scored the player 176 points. Gynaecia is plural for gynoecium, which is the carpels of a flowering plant, collectively. Braconid is a small parasitic wasp.\n" +
                "   The winner said that it was absolutely amazing to be the World Scrabble Champion of 2016. On top of claiming the title, he also collected 7,000 euros. He is going to celebrate the win by going out, buying a bottle of champagne and not playing Scrabble again for the rest of the year.\n", R.drawable.img_scrabble));

        mArticles.add(new Article("El Salvador’s Festival of Fire", "07-09-2016 07:00", "   They say that you shouldn’t play with fire, but in El Salvador it seems hundreds of people have been throwing gasoline-soaked rags at one another. They risked serious burns for an annual church festival in the town of Nejapa.\n" +
                "   The tradition honours a huge volcanic eruption in 1922. The fireball throwing is to remember the old town which the volcano destroyed.\n" +
                "   However, another legend says that the hot lava flow from the volcano was actually the local Christian Saint Geronimo fighting the Devil with balls of fire.\n" +
                "   The festival has been going for more a decade and there is still no set of rules. Authorities are quite concerned that one day things might get out of hand because of that. But for now, despite the obvious dangers, few major injuries have ever been reported.\n",
                R.drawable.img_el_salvador));

        mArticles.add(new Article("People Help a Dog","06-09-2016 15:00","   A dog named Nikki got lost when she was searching for food in a canal in Peru’s rural area of San Martin.\n" +
                "   She later emerged through a hole just big enough for her head to poke through. She was trapped, but people gave her water while they worked out a rescue plan. After residents dug a hole big enough for her to escape, she was finally free, and she walked away with no reported injuries.\n" +
                "   Residents hope that Nikki may now find a comfortable home.\n", R.drawable.img_dog ));

        mArticles.add(new Article("Excited About School","05-09-2016 15:00","   Not everyone is looking forward to going to school, but a pair of twins from London could not be more thrilled. They are now just days from a milestone that many thought they would not reach.\n" +
                "   The pair was born conjoined at the abdomen and they shared just one intestine. They were given only a 25% chance of survival, but an emergency operation saved their lives. Now, four years later after the surgery, they are going to school.\n" +
                "   Their mother explained how amazed she is because all of it came so fast, and she never thought so far ahead. The twins cannot stop talking about school and they can’t wait to be big girls like their older sister.\n", R.drawable.img_school));

        mArticles.add(new Article("Stomach Full of Knives","26-08-2016 07:00","   When Surjeet Singh sought medical advice over a severe stomach ache, doctors thought he might have developed a tumour, but the real reason for his pain left them stunned.\n" +
                "   \"As soon as we placed the camera inside”, the surgeon said, “we saw some metallic material on our screen. In those metallic materials, there were shards of blades and wood which were very unusual. I’ve never seen anything like it in my 20 years of practice. We thought they were knives so we asked the patient, and he said he had this urge to swallow knives. He said he’d swallowed 28 of them.”\n" +
                "   A team of five surgeons operated on Singh for five hours, and say they removed not 28 blades, but 40.\n" +
                "   Singh says he developed the habit of swallowing knives while recuperating from a road accident. He’s now said to be in a stable condition, but we can only hope he's found a safer way of keeping himself occupied while recovering in hospital.\n", R.drawable.img_swords));

        mArticles.add(new Article("Notting Hill Carnival","05-09-2016 07:00","   Notting Hill is a district in North-west London and the place of the annual Notting Hill Carnival. It is Europe’s biggest street party and the second biggest carnival in the world.\n" +
                "   It began in the ‘60s with only a few hundred attendees, but it has grown into a massive party. This year’s carnival was the 50th, and an estimated two million people came. The weather was nice and everybody had a good time.\n" +
                "   However, there were also 9,000 people who were there to work – police officers. Some of them were involved in the fun, but they were there to keep people safe. One policewoman explained that they were trialling a face recognition technology there, which would identify people who should not be at the carnival.\n", R.drawable.img_carnaval));

        mArticles.add(new Article("Largest Aircraft in the World","22-08-2016 15:00","   We have lift-off. The world's largest aircraft has taken to the skies for the first time.\n" +
                "   The Airlander 10 spent nearly two hours in the air, having taken off from Cardington Airfield in Bedfordshire. During its flight, it reached 3,000 feet (914 metres) and performed a series of gentle turns all over a safe area.\n" +
                "   The aircraft is massive – as long as a football pitch and as tall as six double decker buses and capable of flying for up to five days. It was first developed for the US government as a long-range surveillance aircraft but was scrapped following cutbacks.\n" +
                "   Each one cost 25 million pounds and can carry heavier loads than jumbo jets while also producing less noise and emitting less pollution. The makers, Hybrid Air Vehicles, believe it’s the future of aircraft, and that one day we'll be using them to get places.\n" +
                "   But we're a while away yet. The Airlander will need to clock up 200 hours’ flying time before being deemed airworthy by aviation bodies. If it passes though, we can hope we'll all get some extra leg room.\n", R.drawable.img_plane));

    }

    public ArrayList<Article> getmArticles() {
        return mArticles;
    }

}