import React from "react";
import Slider from "react-slick";
import PlaceCard from "../placeCard/placeCard";
import styles from "./placeSlider.module.css";

const PlaceSlider = ({
  sliderRef,
  updateClickedPlace,
  searchPlaces,
  onUpdateMarkerIndex,
  onHandleDelete,
  addShoppingCart,
}) => {
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  return (
    <Slider
      ref={sliderRef}
      {...settings}
      afterChange={(index) => {
        updateClickedPlace(searchPlaces[index]);
        onUpdateMarkerIndex(index);
      }}
    >
      {searchPlaces.map((place, index) => (
        <div key={index} className={styles.placeCardContainer}>
          <PlaceCard
            place={place}
            onHandleDelete={onHandleDelete}
            addShoppingCart={addShoppingCart}
            isLiked={
              false
              // shoppingItems.filter((item) => item.id == place.id).length // 우리 API 호출 시 id가 number, 카카오 API 호출 시 id가 String 얕은 비교
            }
          />
        </div>
      ))}
    </Slider>
  );
};

export default PlaceSlider;
