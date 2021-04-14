import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styles from "./routeDetailPage.module.css";

const RouteDetailPage = (props) => {
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  const images = [
    "https://i.pinimg.com/236x/1a/3f/63/1a3f63dacc72b9ce7a0f41d771068c48.jpg",
    "https://i.pinimg.com/236x/f0/8d/5b/f08d5b455ae78f7c89b99b0354a49b7d.jpg",
    "https://i.pinimg.com/236x/1a/3f/63/1a3f63dacc72b9ce7a0f41d771068c48.jpg",
    "https://i.pinimg.com/236x/f0/8d/5b/f08d5b455ae78f7c89b99b0354a49b7d.jpg",
  ];
  return (
    <div className={styles.RouteDetailPage}>
      <div className={styles.carouselContainer}>
        <Slider {...settings}>
          {images.map((v, index) => (
            <div key={index} className={styles.imageContainer}>
              <img className={styles.img} src={v} alt={"placeImage"} />
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
};

export default RouteDetailPage;
