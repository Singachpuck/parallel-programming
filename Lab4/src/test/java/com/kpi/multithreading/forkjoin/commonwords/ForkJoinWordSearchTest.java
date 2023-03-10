package com.kpi.multithreading.forkjoin.commonwords;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ForkJoinWordSearchTest {

    static Stream<Arguments> wordText() {
        return Stream.of(
            Arguments.of("word", "This word insert word word", 3),
            Arguments.of("the",
                    "The sun was setting behind the mountain range, casting a warm glow on the fields below. The wind whispered through the trees, rustling the leaves as the birds sang their evening songs. The river flowed lazily by, carrying leaves and twigs downstream. The clouds in the sky were painted with hues of orange and pink, signaling the end of another day. The crickets started chirping, and the frogs began their chorus. The scent of freshly cut grass filled the air, reminding the farmer of the day's work. The cows in the pasture grazed peacefully, their bells tinkling in the distance. The world seemed to slow down, as if taking a deep breath before the night began.",
                    22),
            Arguments.of("the", "The old man sat on the bench in the park, watching the children play. The sun shone down on the playground, casting long shadows on the grass. The sound of laughter filled the air as the kids ran around, chasing each other. The trees swayed gently in the breeze, their leaves rustling softly.",
                    12),
            Arguments.of("to",
                    """
                        Nature is a vast and intricate web of life that exists in a myriad of forms and ecosystems, from the depths of the oceans to the highest peaks of the mountains. It is a complex and interconnected network of living organisms that support each other and sustain life on Earth. The natural world provides us with many essential services, including the air we breathe, the water we drink, and the food we eat. It also plays a critical role in regulating the climate, purifying the air and water, and supporting biodiversity.
                                
                        One of the most remarkable aspects of nature is its incredible diversity. Each region of the world has its unique flora and fauna, shaped by its geography, climate, and geology. The tropical rainforests of South America, for example, are home to a vast array of species, including monkeys, sloths, jaguars, and toucans. Meanwhile, the arctic tundras of the North Pole are home to polar bears, reindeer, and arctic foxes. The deserts of the Middle East are home to camels, snakes, and scorpions. The sheer variety of life on Earth is awe-inspiring, and it is a testament to the power and complexity of nature.
                        
                        One of the critical roles of nature is to provide us with the air we breathe. Trees, for example, play a vital role in regulating the Earth's climate and purifying the air. Trees absorb carbon dioxide, a greenhouse gas that contributes to climate change, and release oxygen, which is essential for life. The Amazon rainforest, also known as the "lungs of the Earth," produces 20% of the world's oxygen. However, this critical service is under threat due to deforestation and the loss of natural habitats.
                        
                        Another essential service that nature provides is the production of food. Agriculture is dependent on the natural world, from the pollination of crops by bees and butterflies to the fertilization of soil by earthworms and other organisms. Even seafood, a critical source of protein for millions of people worldwide, is dependent on the health of the ocean's ecosystems. However, overfishing and pollution are putting immense pressure on the world's oceans, threatening the health of marine life and the livelihoods of millions of people.
                        
                        Biodiversity is another critical service that nature provides. The world's ecosystems are incredibly complex and interconnected, with each species playing a vital role in maintaining the balance. The loss of one species can have far-reaching consequences, leading to the collapse of entire ecosystems. The extinction of the dinosaurs, for example, had a profound impact on the evolution of life on Earth. While the loss of one species may seem insignificant, it can have a ripple effect throughout the entire ecosystem.
                        
                        Despite the critical role that nature plays in our lives, it is under threat from a range of human activities. Climate change, deforestation, pollution, and habitat destruction are all taking a toll on the natural world. The Earth's ecosystems are interconnected, and the loss of one species can have far-reaching consequences. For example, the loss of bees, which play a critical role in pollination, could result in the loss of many crops and the collapse of entire ecosystems. Furthermore, climate change is leading to rising sea levels, more frequent and severe natural disasters, and the extinction of many species.
                        
                        Fortunately, there is still hope. Individuals, organizations, and governments are taking steps to protect and preserve the natural world. Conservation efforts have led to the recovery of species such as the bald eagle and the gray wolf, which were once on the brink of extinction. Renewable energy sources such as solar and wind power are reducing our dependence on fossil fuels and mitigating climate change. Protected areas, such as national parks and wildlife reserves, are preserving critical habitats and ecosystems. These efforts are essential in ensuring that future generations can enjoy the benefits of a healthy and vibrant natural world.
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
                        Education and awareness are also crucial in promoting conservation efforts. By learning about the natural world and the threats it faces, we can make informed decisions about our actions and lifestyles. We can reduce our carbon footprint by using public transportation, consuming less meat, and using energy-efficient appliances. We can also support conservation organizations and volunteer our time to help protect and preserve natural habitats.
                           
                        In conclusion, nature is a complex and interconnected web of life that provides us with many essential services. From the air we breathe to the food we eat, nature plays a critical role in sustaining life on Earth. However, human activities such as deforestation, pollution, and climate change are putting immense pressure on the natural world. It is essential that we take steps to protect and preserve the natural world, for the sake of future generations and the health of our planet. By working together, we can ensure that nature continues to thrive and support life on Earth for many generations to come.
                                                
                        Protected areas, such as national parks and wildlife reserves, are also essential in preserving the natural world. These areas provide critical habitats for endangered species and allow ecosystems to thrive without interference from human activities. They also provide opportunities for individuals to connect with nature and appreciate its beauty and complexity.
                                                    
                        Furthermore, research and innovation can play a vital role in protecting the natural world. Scientists are developing new technologies and methods to mitigate the impacts of climate change, such as carbon capture and storage and renewable energy sources. They are also working to develop sustainable agriculture practices and reduce the use of harmful chemicals and pesticides.
                                                    
                        Finally, it is essential to recognize the cultural and spiritual significance of nature. Indigenous peoples have long understood the importance of a healthy and vibrant natural world and have developed practices and traditions that promote sustainability and conservation. By valuing and respecting these cultural traditions, we can learn from their wisdom and incorporate it into our conservation efforts.
                                                    
                        In conclusion, the natural world is a precious and valuable resource that provides us with many essential services. It is under threat from human activities, but there is still hope. By taking steps to protect and preserve the natural world, such as conservation efforts, education, research, and innovation, we can ensure that it continues to support life on Earth. We must also recognize the cultural and spiritual significance of nature and work to incorporate traditional knowledge and practices into our conservation efforts. By working together, we can create a more sustainable and healthy planet for all.
                    """, 26)
        );
    }

    @ParameterizedTest
    @MethodSource("wordText")
    void wordSearchTest(String word, String text, int amount) {
        final ForkJoinWordSearch wordSearch = new ForkJoinWordSearch();
        final List<Position> positions = wordSearch.search(word, text);
        positions
                .forEach(pos -> {
                    assertEquals(word.length(), pos.getEnd() - pos.getBegin());
                    System.out.println(pos.getBegin() + " " + pos.getEnd());
                });

        assertEquals(amount, positions.size());
    }

}