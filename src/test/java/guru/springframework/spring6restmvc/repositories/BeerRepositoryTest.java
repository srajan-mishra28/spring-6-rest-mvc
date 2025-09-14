package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.mappers.BeerMapperImpl;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;


    @Test
    void testSaveBeer() {
        BeerMapper beerMapper = Mappers.getMapper(BeerMapper.class);
        BeerDTO beer1 = BeerDTO.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beerE1 = beerMapper.beerDtoToBeer(beer1);
        Beer beerE2 = beerMapper.beerDtoToBeer(beer2);
        Beer beerE3 = beerMapper.beerDtoToBeer(beer3);

        Beer b1=beerRepository.save(beerE1);
        Beer b2 =beerRepository.save(beerE2);
        Beer b3 = beerRepository.save(beerE3);


        assertThat(b1).isNotNull();
        assertThat(b1.getId()).isNotNull();
        assertThat(b2).isNotNull();
        assertThat(b2.getId()).isNotNull();
        assertThat(b3).isNotNull();
        assertThat(b3.getId()).isNotNull();
    }
}