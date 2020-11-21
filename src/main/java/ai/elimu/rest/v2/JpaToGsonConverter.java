package ai.elimu.rest.v2;

import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.gson.application.ApplicationGson;
import ai.elimu.model.v2.gson.application.ApplicationVersionGson;
import ai.elimu.model.v2.gson.content.AllophoneGson;
import ai.elimu.model.v2.gson.content.EmojiGson;
import ai.elimu.model.v2.gson.content.ImageGson;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.model.v2.gson.content.LetterToAllophoneMappingGson;
import ai.elimu.model.v2.gson.content.NumberGson;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.model.v2.gson.crowdsource.WordContributionEventGson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Convert classes from JPA/Hibernate format to POJO format, so that they can be serialized into 
 * JSON and transferred to Android applications that are connecting via the REST API.
 */
public class JpaToGsonConverter {
    
    public static LetterGson getLetterGson(Letter letter) {
        if (letter == null) {
            return null;
        } else {
            LetterGson letterGson = new LetterGson();
            
            // BaseEntity
            letterGson.setId(letter.getId());
            
            // Content
            letterGson.setRevisionNumber(letter.getRevisionNumber());
            letterGson.setUsageCount(letter.getUsageCount());
            
            // Letter
            letterGson.setText(letter.getText());
            letterGson.setDiacritic(letter.isDiacritic());
            
            return letterGson;
        }
    }
    
    public static AllophoneGson getAllophoneGson(Allophone allophone) {
        if (allophone == null) {
            return null;
        } else {
            AllophoneGson allophoneGson = new AllophoneGson();
            
            // BaseEntity
            allophoneGson.setId(allophone.getId());
            
            // Content
            allophoneGson.setRevisionNumber(allophone.getRevisionNumber());
            allophoneGson.setUsageCount(allophone.getUsageCount());
            
            // Allophone
            allophoneGson.setValueIpa(allophone.getValueIpa());
            allophoneGson.setDiacritic(allophone.isDiacritic());
            allophoneGson.setSoundType(allophone.getSoundType());
            
            return allophoneGson;
        }
    }
    
    public static LetterToAllophoneMappingGson getLetterToAllophoneMappingGson(LetterToAllophoneMapping letterToAllophoneMapping) {
        if (letterToAllophoneMapping == null) {
            return null;
        } else {
            LetterToAllophoneMappingGson letterToAllophoneMappingGson = new LetterToAllophoneMappingGson();
            
            // BaseEntity
            letterToAllophoneMappingGson.setId(letterToAllophoneMapping.getId());
            
            // LetterToAllophoneMapping
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : letterToAllophoneMapping.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            letterToAllophoneMappingGson.setLetters(letters);
            List<AllophoneGson> allophones = new ArrayList<>();
            for (Allophone allophone : letterToAllophoneMapping.getAllophones()) {
                AllophoneGson allophoneGson = getAllophoneGson(allophone);
                allophones.add(allophoneGson);
            }
            letterToAllophoneMappingGson.setAllophones(allophones);
            letterToAllophoneMappingGson.setUsageCount(letterToAllophoneMapping.getUsageCount());
            
            return letterToAllophoneMappingGson;
        }
    }
    
    public static WordGson getWordGson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordGson wordGson = new WordGson();
            
            // BaseEntity
            wordGson.setId(word.getId());
            
            // Content
            wordGson.setRevisionNumber(word.getRevisionNumber());
            wordGson.setUsageCount(word.getUsageCount());
            
            // Word
            wordGson.setText(word.getText());
            List<LetterToAllophoneMappingGson> letterToAllophoneMappings = new ArrayList<>();
            for (LetterToAllophoneMapping letterToAllophoneMapping : word.getLetterToAllophoneMappings()) {
                LetterToAllophoneMappingGson letterToAllophoneMappingGson = getLetterToAllophoneMappingGson(letterToAllophoneMapping);
                letterToAllophoneMappings.add(letterToAllophoneMappingGson);
            }
            wordGson.setLetterToAllophoneMappings(letterToAllophoneMappings);
            wordGson.setWordType(word.getWordType());
            
            return wordGson;
        }
    }
    
    public static NumberGson getNumberGson(Number number) {
        if (number == null) {
            return null;
        } else {
            NumberGson numberGson = new NumberGson();
            
            // BaseEntity
            numberGson.setId(number.getId());
            
            // Content
            numberGson.setRevisionNumber(number.getRevisionNumber());
            numberGson.setUsageCount(number.getUsageCount());
            
            // Number
            numberGson.setValue(number.getValue());
            numberGson.setSymbol(number.getSymbol());
            
            return numberGson;
        }
    }
    
    public static WordContributionEventGson getWordContributionEventGson(WordContributionEvent wordContributionEvent) {
        if (wordContributionEvent == null) {
            return null;
        } else {
            WordContributionEventGson wordContributionEventGson = new WordContributionEventGson();
            
            // BaseEntity
            wordContributionEventGson.setId(wordContributionEvent.getId());
            
            // WordContributionEvent
            wordContributionEventGson.setWord(getWordGson(wordContributionEvent.getWord()));
            wordContributionEventGson.setComment(wordContributionEvent.getComment());
            wordContributionEventGson.setTime(wordContributionEvent.getTime());
            
            return wordContributionEventGson;
        }
    }
    
    public static EmojiGson getEmojiGson(Emoji emoji) {
        if (emoji == null) {
            return null;
        } else {
            EmojiGson emojiGson = new EmojiGson();
            
            // BaseEntity
            emojiGson.setId(emoji.getId());
            
            // Content
            emojiGson.setRevisionNumber(emoji.getRevisionNumber());
            emojiGson.setUsageCount(emoji.getUsageCount());
            
            // Emoji
            emojiGson.setGlyph(emoji.getGlyph());
            emojiGson.setUnicodeVersion(emoji.getUnicodeVersion());
            emojiGson.setUnicodeEmojiVersion(emoji.getUnicodeEmojiVersion());
            Set<WordGson> wordGsons = new HashSet<>();
            for (Word word : emoji.getWords()) {
                WordGson wordGson = new WordGson();
                wordGson.setId(word.getId());
                wordGsons.add(wordGson);
            }
            emojiGson.setWords(wordGsons);
            
            return emojiGson;
        }
    }
    
    public static ImageGson getImageGson(Image image) {
        if (image == null) {
            return null;
        } else {
            ImageGson imageGson = new ImageGson();
            
            // BaseEntity
            imageGson.setId(image.getId());
            
            // Content
            imageGson.setRevisionNumber(image.getRevisionNumber());
            imageGson.setUsageCount(image.getUsageCount());
            
            // Image
            imageGson.setTitle(image.getTitle());
            imageGson.setImageFormat(image.getImageFormat());
            imageGson.setDownloadUrl("/image/" + image.getId() + "_r" + image.getRevisionNumber() + "." + image.getImageFormat().toString().toLowerCase());
            imageGson.setDownloadSize(image.getBytes().length / 1024);
            Set<WordGson> wordGsons = new HashSet<>();
            for (Word word : image.getWords()) {
                WordGson wordGson = new WordGson();
                wordGson.setId(word.getId());
                wordGsons.add(wordGson);
            }
            imageGson.setWords(wordGsons);
            
            return imageGson;
        }
    }
    
    public static StoryBookGson getStoryBookGson(StoryBook storyBook) {
        if (storyBook == null) {
            return null;
        } else {
            StoryBookGson storyBookGson = new StoryBookGson();
            
            // BaseEntity
            storyBookGson.setId(storyBook.getId());
            
            // Content
            storyBookGson.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookGson.setUsageCount(storyBook.getUsageCount());
            
            // StoryBook
            storyBookGson.setTitle(storyBook.getTitle());
            storyBookGson.setDescription(storyBook.getDescription());
            if (storyBook.getCoverImage() != null) {
                ImageGson imageGson = new ImageGson();
                imageGson.setId(storyBook.getCoverImage().getId());
                storyBookGson.setCoverImage(imageGson);
            }
            storyBookGson.setReadingLevel(storyBook.getReadingLevel());
            
            
            return storyBookGson;
        }
    }
    
    public static StoryBookChapterGson getStoryBookChapterGson(StoryBookChapter storyBookChapter) {
        if (storyBookChapter == null) {
            return null;
        } else {
            StoryBookChapterGson storyBookChapterGson = new StoryBookChapterGson();
            
            // BaseEntity
            storyBookChapterGson.setId(storyBookChapter.getId());
            
            // StoryBookChapter
            storyBookChapterGson.setSortOrder(storyBookChapter.getSortOrder());
            if (storyBookChapter.getImage() != null) {
                ImageGson imageGson = new ImageGson();
                imageGson.setId(storyBookChapter.getImage().getId());
                storyBookChapterGson.setImage(imageGson);
            }
            
            return storyBookChapterGson;
        }
    }
    
    public static StoryBookParagraphGson getStoryBookParagraphGson(StoryBookParagraph storyBookParagraph) {
        if (storyBookParagraph == null) {
            return null;
        } else {
            StoryBookParagraphGson storyBookParagraphGson = new StoryBookParagraphGson();
            
            // BaseEntity
            storyBookParagraphGson.setId(storyBookParagraph.getId());
            
            // StoryBookParagraph
            storyBookParagraphGson.setSortOrder(storyBookParagraph.getSortOrder());
            storyBookParagraphGson.setOriginalText(storyBookParagraph.getOriginalText());
            List<WordGson> wordGsons = new ArrayList<>();
            for (Word word : storyBookParagraph.getWords()) {
                WordGson wordGson = null;
                if (word != null) {
                    wordGson = new WordGson();
                    wordGson.setId(word.getId());
                }
                wordGsons.add(wordGson);
            }
            storyBookParagraphGson.setWords(wordGsons);
            
            return storyBookParagraphGson;
        }
    }
    
    public static ApplicationGson getApplicationGson(Application application) {
        if (application == null) {
            return null;
        } else {
            ApplicationGson applicationGson = new ApplicationGson();
            
            // BaseEntity
            applicationGson.setId(application.getId());
            
            // Application
            applicationGson.setPackageName(application.getPackageName());
            applicationGson.setInfrastructural(application.isInfrastructural());
            applicationGson.setLiteracySkills(application.getLiteracySkills());
            applicationGson.setNumeracySkills(application.getNumeracySkills());
            applicationGson.setApplicationStatus(application.getApplicationStatus());
            
            return applicationGson;
        }
    }
    
    public static ApplicationVersionGson getApplicationVersionGson(ApplicationVersion applicationVersion) {
        if (applicationVersion == null) {
            return null;
        } else {
            ApplicationVersionGson applicationVersionGson = new ApplicationVersionGson();
            
            // BaseEntity
            applicationVersionGson.setId(applicationVersion.getId());
            
            // ApplicationVersion
            applicationVersionGson.setFileUrl("/apk/" + applicationVersion.getApplication().getPackageName() + "-" + applicationVersion.getVersionCode() + ".apk");
            applicationVersionGson.setFileSizeInKb(applicationVersion.getFileSizeInKb());
            applicationVersionGson.setChecksumMd5(applicationVersion.getChecksumMd5());
            applicationVersionGson.setVersionCode(applicationVersion.getVersionCode());
            
            return applicationVersionGson;
        }
    }
}
