import React, { useCallback, useRef, useState } from "react";
import styles from "./hotplacePage.module.css";
import HotplaceMap from "../../components/map/hotplaceMap/hotplaceMap";
import PortalCart from "../../containers/portalCart/portalCart";
import useInput from "../../hooks/useInput";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Drawer } from "antd";
import ShoppingCart from "../../components/common/shoppingCart/shoppingCart";
import DeleteConfirm from "../../components/common/deleteConfirm/deleteConfirm";
import CategoryBar from "../../components/map/hotplaceMap/categoryBar/categoryBar";
import PlaceCard from "../../components/placeCard/placeCard";
import "react-responsive-carousel/lib/styles/carousel.min.css";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const HotplacePage = () => {
  const placeListRef = useRef();

  const [cartVisible, setCartVisible] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState(null);
  const [confirmPortal, setConfirmPortal] = useState(false);
  const [cartPortal, setCartPortal] = useState(false);
  const [place, setPlace] = useState({});
  const [inputKeyword, handleInputKeyword] = useInput();
  const [searchPlace, setSearchPlace] = useState("");
  const [searchPlaces, setSearchPlaces] = useState([]);
  const [markerIndex, setMarkerIndex] = useState();

  const setCartVisibleTrue = () => {
    setCartVisible(true);
  };
  const setCartVisibleFalse = () => {
    setCartVisible(false);
  };
  const setConfirmPortalTrue = () => {
    setConfirmPortal(true);
  };
  const setConfirmPortalFalse = () => {
    setConfirmPortal(false);
  };
  const handleDeleteItem = (id) => {
    console.log("삭제");
  };
  const handleCartPortalClose = useCallback(() => {
    setCartPortal(false);
  }, []);
  const handleCartPortalOpen = useCallback(() => {
    setCartPortal(true);
  }, []);
  const updateClickedPlace = useCallback((place) => {
    setPlace(place);
  }, []);
  const updateSearchPlaces = useCallback((places) => {
    if (places.length > 0) {
      placeListRef.current.style.display = "initial";
    }
    setSearchPlaces(places);
  }, []);
  const handleSubmit = useCallback(
    (e) => {
      e.preventDefault();
      if (inputKeyword === searchPlace) {
        setSearchPlace(inputKeyword + " ");
      } else {
        setSearchPlace(inputKeyword);
      }
    },
    [inputKeyword, searchPlace]
  );
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  return (
    <div className={styles.HotplacePage}>
      <div className={styles.searchContainer}>
        <form className={styles.form} onSubmit={handleSubmit}>
          <input
            className={styles.inputBar}
            placeholder="Search place..."
            onChange={handleInputKeyword}
            value={inputKeyword || ""}
          />
        </form>
        <div className={styles.toggle}>
          <ShoppingCartOutlined
            className={styles.cartIcon}
            onClick={setCartVisibleTrue}
          />
        </div>
      </div>
      <div className={styles.mapContainer}>
        <HotplaceMap
          handleCartPortalOpen={handleCartPortalOpen}
          updateClickedPlace={updateClickedPlace}
          searchPlace={searchPlace}
          updateSearchPlaces={updateSearchPlaces}
          place={place}
          markerIndex={markerIndex}
        />
      </div>
      <div ref={placeListRef} className={styles.carouselContainer}>
        <Slider
          {...settings}
          afterChange={(index) => {
            updateClickedPlace(searchPlaces[index]);
            setMarkerIndex(index);
          }}
        >
          {searchPlaces.map((place, index) => (
            <div key={index} className={styles.placeCardContainer}>
              <PlaceCard place={place} onHandleDelete={handleDeleteItem} />
            </div>
          ))}
        </Slider>
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
      <Drawer
        title={`${"장소"}의 담은 목록`}
        placement="right"
        closable={true}
        onClose={setCartVisibleFalse}
        visible={cartVisible}
        width={window.innerWidth > 768 ? "36vw" : "80vw"}
        bodyStyle={{
          backgroundColor: "#ebecec",
          padding: 0,
        }}
        zIndex={1004}
      >
        <ShoppingCart setConfirmPortalTrue={setConfirmPortalTrue} />
      </Drawer>
      {confirmPortal && (
        <DeleteConfirm
          onDeleteItem={handleDeleteItem}
          onClose={setConfirmPortalFalse}
        />
      )}
      {cartPortal && (
        <PortalCart
          place={place}
          handleCartPortalClose={handleCartPortalClose}
        />
      )}
    </div>
  );
};

export default HotplacePage;
