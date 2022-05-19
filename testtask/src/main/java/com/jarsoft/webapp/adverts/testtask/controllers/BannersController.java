package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.exception.BadNameException;
import com.jarsoft.webapp.adverts.testtask.exception.NotUniqueNameException;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.repositories.BannerRepository;
import com.jarsoft.webapp.adverts.testtask.security.SqlInjectionSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * <h1>This RestController exists to work with banners and return some information to BannerService</h1>
 *
 */
@RestController()
@RequestMapping("/root/api")
public class BannersController {
    private BannerRepository bannerRepository;
    @Autowired
    public void setBannerRepository(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }


    /**
     * <h1>This function called by BannerService to get Banners by name</h1>
     *
     * @param searchValue PathVariable
     * @return List<BannerEntity> filtered by searchValue
     * @throws BadNameException if searchValue contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/BannerService.js">src/main/js/services/BannerService.js</a>
     */
    @GetMapping("/search/{searchValue}")
    public List<BannerEntity> viewCertainBanners(@PathVariable String searchValue) throws BadNameException {
        if(SqlInjectionSecurity.check(searchValue)) throw new BadNameException();
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeletedBySearch(searchValue.toLowerCase());
        return StreamSupport.stream(banners.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * <h1>This function called by BannerService to get all not deleted Banners</h1>
     *
     * @see <a href="..src/main/js/services/BannerService.js">src/main/js/services/BannerService.js</a>
     * @return {@code List<BannerEntity>} not deleted banners from banner_entity table
     *
     */
    @GetMapping("/search/")
    public List<BannerEntity> viewAllBanners() {
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeleted();
        return StreamSupport.stream(banners.spliterator(), false).toList();
    }

    /**
     * <h1>This function called by BannerService to get Banner by id</h1>
     *
     * @param bid BannerEntity.IdBanner
     * @return BannerEntity or ResourceNotFoundException
     */
    @GetMapping("/{bid}")
    public BannerEntity viewEditBannerField(@PathVariable("bid") Long bid) {
        return bannerRepository.findById(bid).orElseThrow(() -> new ResourceNotFoundException("Banner not exist with id :" + bid));
    }


    /**
     * <h1>This function called by BannerService to update Banner by id</h1>
     *
     * @param bid BannerEntity.IdBanner
     * @param bannerDetails new BannerEntity
     * @return ResponseEntity
     * @throws NotUniqueNameException if banner with same name already exist
     * @throws BadNameException if new BannerEntity.name contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/BannerService.js">src/main/js/services/BannerService.js</a>
     */
    @PostMapping("/{bid}")
    public ResponseEntity<BannerEntity> updateBanner(@PathVariable Long bid,
                                                     @Valid @RequestBody BannerEntity bannerDetails) throws NotUniqueNameException, BadNameException {

        if(SqlInjectionSecurity.check(bannerDetails.getName())) throw new BadNameException();
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeletedByName(bannerDetails.getName());
        for (BannerEntity banner: banners) {
            if(!Objects.equals(banner.getIdBanner(), bid)) {
                if (banner.getName().equals(bannerDetails.getName())) {  //You should re-control result, because query used LOWERCASE context
                    throw new NotUniqueNameException();
                }
            }
        }

        BannerEntity banner = bannerRepository.findById(bid).orElseThrow(() -> new ResourceNotFoundException("Banner not exist with id :" + bid));
        banner.setName(bannerDetails.getName());
        banner.setPrice(bannerDetails.getPrice());
        banner.setText(bannerDetails.getText());
        banner.setCategories(bannerDetails.getCategories());
        banner.setDeleted(false);
        BannerEntity updatedBannerEntity = bannerRepository.save(banner);

        return ResponseEntity.ok(updatedBannerEntity);
    }


    /**
     * <h1>This function called by BannerService to create Banner</h1>
     *
     * @param newBanner BannerEntity with completed fields
     * @return BannerEntity which was created
     * @throws NotUniqueNameException if banner with same name already exist
     * @throws BadNameException if new BannerEntity.name contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/BannerService.js">src/main/js/services/BannerService.js</a>
     */
    @PostMapping("/create")
    public BannerEntity createBanner(@Valid @RequestBody BannerEntity newBanner) throws NotUniqueNameException, BadNameException {
        if(SqlInjectionSecurity.check(newBanner.getName())) throw new BadNameException();
        Iterable<BannerEntity> banners = bannerRepository.findAllNotDeletedByName(newBanner.getName());
        for (BannerEntity banner: banners) {
            if (banner.getName().equals(newBanner.getName())) {  //You should re-control result, because query used LOWERCASE context
                throw new NotUniqueNameException();
            }
        }
        return bannerRepository.save(newBanner);
    }

    /**
     * <h1>This function called by BannerService to delete Banner by id</h1>
     *
     * @param bid Banner id which will be deleted
     * @return ResponseEntity
     * @throws ResourceNotFoundException if Banner with such bid not exist
     * @see <a href="..src/main/js/services/BannerService.js">src/main/js/services/BannerService.js</a>
     */
    @DeleteMapping("/{bid}")
    public ResponseEntity<Boolean> deleteBanner(@PathVariable Long bid){
        BannerEntity banner = bannerRepository.findById(bid)
                .orElseThrow(() -> new ResourceNotFoundException("Banner not exist with id :" + bid));
        banner.setDeleted(true);
        bannerRepository.save(banner);
        return ResponseEntity.ok(true);
    }

}
