package com.kpi.multithreading.forkjoin.textkeyword;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TextKeywordMatcherTest {

    static Stream<Arguments> keywordsText() {
        return Stream.of(
                Arguments.of(new String[] {"nature", "planet", "healthy"},
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
                        """),
                Arguments.of(new String[] {"information", "technologies", "industry", "cybersecurity"},
                        """
                            Information technologies (IT) refer to the use of computers, software, and other electronic devices to process, store, and transmit information. IT has transformed the way we communicate, work, and live. With the increasing reliance on technology, it has become an integral part of every aspect of our daily lives.
                                                            
                            One of the most significant impacts of information technology is the internet. It has revolutionized the way we access and share information, connecting people from all over the world and enabling instant communication. The development of social media platforms and messaging apps has made it easier than ever to stay in touch with friends, family, and colleagues.
                                                            
                            Businesses have also benefited greatly from IT. Companies use computer programs and other software to streamline their operations, manage finances, and analyze data to make informed decisions. The use of online marketing and e-commerce has also helped businesses expand their reach and increase sales.
                                                            
                            The healthcare industry has also been transformed by IT. Electronic medical records (EMRs) have replaced paper records, making it easier for doctors to access patient information quickly and accurately. Telemedicine, which allows healthcare professionals to provide remote care, has become more prevalent, especially during the COVID-19 pandemic.
                                                            
                            However, IT also poses risks, such as cyber attacks and data breaches. As technology continues to advance, it's important to prioritize cybersecurity and protect sensitive information.
                                                            
                            In conclusion, IT has had a significant impact on society, transforming the way we live, work, and communicate. While it offers many benefits, it's important to be aware of its risks and use it responsibly.
                        """)
        );
    }

    @ParameterizedTest
    @MethodSource("keywordsText")
    void matchesKeywordsTest(String[] keywords, String text) {
        final TextKeywordMatcher textKeywordMatcher = new TextKeywordMatcher();
        assertTrue(textKeywordMatcher.matchesKeywords(keywords, text));
    }
}