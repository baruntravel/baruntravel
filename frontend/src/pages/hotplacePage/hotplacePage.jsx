import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./hotplacePage.module.css";
import HotplaceMap from "../../components/map/hotplaceMap/hotplaceMap";
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
import { useRecoilState, useRecoilValue } from "recoil";
import { userState, userCart } from "../../recoil/userState";
import PortalAuth from "../../containers/portalAuth/portalAuth";

import {
  onAddCart,
  onDeleteCartItem,
  onDeleteCartAll,
  onReceiveCart,
} from "../../api/cartAPI";
const HotplacePage = () => {
  const placeListRef = useRef();
  const searchRef = useRef();
  const inputRef = useRef();
  const sliderRef = useRef();

  // recoil과 beautiful-dnd가 concurrent 문제로 충돌이 나여 전역관리와 페이지 단 관리 두가지를 모두해줘야함.
  const [shoppingItemsRecoil, setShoppingItemsRecoil] = useRecoilState(
    userCart
  );
  const [shoppingItems, setShoppingItems] = useState([]);

  const userStates = useRecoilValue(userState);
  const [cartVisible, setCartVisible] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState(null);
  const [confirmPortal, setConfirmPortal] = useState(false);
  const [place, setPlace] = useState({});
  const [inputKeyword, handleInputKeyword] = useInput();
  const [searchPlace, setSearchPlace] = useState("");
  const [searchPlaces, setSearchPlaces] = useState([]);
  const [markerIndex, setMarkerIndex] = useState();

  useEffect(() => {
    if (userStates.isLogin) {
      setShoppingItems(shoppingItemsRecoil);
    }
  }, [userStates]);
  useEffect(() => {
    setShoppingItemsRecoil(shoppingItems);
  }, [shoppingItems]);
  const [needLogin, setNeedLogin] = useState(false);

  const setCartVisibleTrue = useCallback(() => {
    if (userStates.isLogin) {
      setCartVisible(true);
    } else {
      setNeedLogin(true);
    }
  }, [userStates]);
  const setCartVisibleFalse = useCallback(() => {
    setCartVisible(false);
  }, []);
  const setConfirmPortalTrue = useCallback(() => {
    setConfirmPortal(true);
  }, []);
  const setConfirmPortalFalse = useCallback(() => {
    setConfirmPortal(false);
  }, []);
  const handleDeleteItem = useCallback(
    async (id) => {
      setShoppingItems((prev) => {
        const updated = prev.filter((item) => item.id !== id);
        return updated;
      });
      await onDeleteCartItem(id);
    },
    [setShoppingItems]
  );
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

  const clickedMarker = useCallback((index) => {
    sliderRef.current.slickGoTo(index);
  }, []);

  const deleteClickedItemId = useCallback((id) => {
    setDeleteItemId(id);
  }, []);

  const addShoppingCart = useCallback(
    (place) => {
      if (userStates.isLogin) {
        setShoppingItems((prev) => {
          const updated = [...prev, place];
          return updated;
        });
        onAddCart(place);
      } else {
        setNeedLogin(true);
      }
    },
    [setShoppingItems, userStates]
  );

  const updateShoppingCart = useCallback(
    (items) => {
      setShoppingItems(items);
    },
    [setShoppingItems]
  );

  const updateMemoShoppingItem = useCallback(
    (id, memo) => {
      setShoppingItems((prev) => {
        const updated = [...prev];
        const forUpdateIndex = updated.findIndex((item) => {
          if (item.id === id) return true;
        });
        updated[forUpdateIndex] = { ...updated[forUpdateIndex], memo: memo };
        return updated;
      });
    },
    [setShoppingItems]
  );

  const resetCartAll = useCallback(async () => {
    setShoppingItems([]);
    await onDeleteCartAll();
  }, [setShoppingItems]);

  const portalAuthClose = useCallback(() => {
    setNeedLogin(false);
  }, []);
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
        <form ref={searchRef} className={styles.form} onSubmit={handleSubmit}>
          <input
            ref={inputRef}
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
          searchRef={searchRef}
          inputRef={inputRef}
          updateClickedPlace={updateClickedPlace}
          updateSearchPlaces={updateSearchPlaces}
          place={place}
          markerIndex={markerIndex}
          clickedMarker={clickedMarker}
        />
      </div>
      <div ref={placeListRef} className={styles.carouselContainer}>
        <Slider
          ref={sliderRef}
          {...settings}
          afterChange={(index) => {
            updateClickedPlace(searchPlaces[index]);
            setMarkerIndex(index);
          }}
        >
          {searchPlaces.map((place, index) => (
            <div key={index} className={styles.placeCardContainer}>
              <PlaceCard
                place={place}
                onHandleDelete={handleDeleteItem}
                addShoppingCart={addShoppingCart}
                isLiked={
                  shoppingItems.filter((item) => item.id == place.id).length
                }
              />
            </div>
          ))}
        </Slider>
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
      {!needLogin && (
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
          <ShoppingCart
            deleteClickedItemId={deleteClickedItemId}
            setConfirmPortalTrue={setConfirmPortalTrue}
            updateShoppingCart={updateShoppingCart}
            updateMemoShoppingItem={updateMemoShoppingItem}
            resetCartAll={resetCartAll}
            items={shoppingItems}
          />
        </Drawer>
      )}
      {confirmPortal && (
        <DeleteConfirm
          deleteItemId={deleteItemId}
          onDeleteItem={handleDeleteItem}
          onClose={setConfirmPortalFalse}
        />
      )}
      {needLogin && <PortalAuth onClose={portalAuthClose} />}
    </div>
  );
};

export default HotplacePage;
