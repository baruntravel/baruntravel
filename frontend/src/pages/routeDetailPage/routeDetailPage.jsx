import React from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styles from "./routeDetailPage.module.css";
import DetailHeader from "../../components/detailHeader/detailHeader";
import ImageMap from "../../components/map/imageMap/imageMap";
import PlaceCard from "../../components/placeCard/placeCard";

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
  const places = [
    {
      id: "688578118",
      place_name: "BNK저축은행 리테일금융센터",
      place_url: "http://place.map.kakao.com/688578118",
      address_name: "서울 중구 무교로 6",
      x: "126.97943787116054",
      y: "37.56657026127022",
    },
    {
      id: "508437738",
      place_name: "신한은행 서울시청금융센터",
      placeUrl: "http://place.map.kakao.com/508437738",
      address_name: "서울 중구 세종대로 110",
      x: "126.978244947578",
      y: "37.5662231640346",
    },
    {
      id: "7975883",
      place_name: "신한은행 서울광장출장소",
      placeUrl: "http://place.map.kakao.com/7975883",
      address_name: "서울 중구 을지로 16",
      x: "126.979476558519",
      y: "37.5658314512941",
    },
  ];
  return (
    <div className={styles.RouteDetailPage}>
      <DetailHeader />
      <div className={styles.sliderContainer}>
        <Slider {...settings}>
          {images.map((v, index) => (
            <div key={index} className={styles.imageContainer}>
              <img className={styles.img} src={v} alt={"placeImage"} />
            </div>
          ))}
        </Slider>
        <div className={styles.slider__placeNameBox}>
          <span className={styles.slider__placeName}> placeName </span>
        </div>
      </div>
      <div className={styles.titleBox}>
        <h2 className={styles.title}>#해시태그 #제목</h2>
      </div>
      <div className={styles.contentBox}>
        <span className={styles.content}>이 장소를 다녀왔어요</span>
      </div>
      <div className={styles.mapImage}>
        <ImageMap />
      </div>
      <div className={styles.placesBox}>
        {places &&
          places.map((place, index) => (
            <div key={index} className={styles.placeBox}>
              <PlaceCard place={place} />
            </div>
          ))}
      </div>
    </div>
  );
};

export default RouteDetailPage;
