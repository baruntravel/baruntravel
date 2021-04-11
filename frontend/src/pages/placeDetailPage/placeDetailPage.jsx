import React from "react";
import DetailHeader from "../../components/detailHeader/detailHeader";
import styles from "./placeDetailPage.module.css";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const PlaceDetailPage = (props) => {
  const testImages = [
    "https://blog.hmgjournal.com/images_n/contents/171013_N1.png",
    "https://blog.hmgjournal.com/images_n/contents/171013_N1.png",
    "https://blog.hmgjournal.com/images_n/contents/171013_N1.png",
    "https://blog.hmgjournal.com/images_n/contents/171013_N1.png",
  ];
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className={styles.PlaceDetailPage}>
      <DetailHeader />
      <div className={styles.slideContainer}>
        <Slider {...settings}>
          {testImages.map((imgSrc) => (
            <div className={styles.imageContainer}>
              <img className={styles.image} src={imgSrc} alt="placeImage" />
            </div>
          ))}
        </Slider>
        <div className={styles.imageCounter}>
          <span> 1 / 21 </span>
        </div>
      </div>
    </div>
  );
};

export default PlaceDetailPage;
