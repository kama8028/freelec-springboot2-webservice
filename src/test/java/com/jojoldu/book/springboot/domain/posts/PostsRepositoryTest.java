package com.jojoldu.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    //Junit 단위테스트가 끌날 때마다 수행되는 메소드
    //여러 테스트가 동시에 수행되면 테스트용 데이터베이스 H2에 데이터가 그래도 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있다.
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트게시글";
        String content = "테스트본문";

        // 테이블 posts에 insert/update 쿼리를 실행합니다. id 값이 있으면 update, 없다면 insert가 실행됩니다.
        postsRepository.save(Posts.builder()
        .title(title)
        .content(content)
        .author("kama8028@naver.com")
        .build());

        //when
        //posts에 있는 모든 데이터를 조회해 오는 메소드 입니다.
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate="+posts.getCreateDate()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
