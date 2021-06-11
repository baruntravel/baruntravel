import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./kakaoMapPage.module.css";
import HotplaceMap from "../../components/kakaoMapPage/kakaoMap/kakaoMap";
import useInput from "../../hooks/useInput";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { Drawer } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faList } from "@fortawesome/free-solid-svg-icons";
import ShoppingCart from "../../components/common/shoppingCart/shoppingCart";
import CategoryBar from "../../components/common/categoryBar/categoryBar";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { userState, userCart } from "../../recoil/userState";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import { onAddCart, onDeleteCart, onDeleteCartAll, onReceiveCart } from "../../api/cartAPI";
import SideProfileToggle from "../../components/common/sideProfileToggle/sideProfileToggle";
import PortalPlaceList from "../../components/portal/portalPlaceList/portalPlaceList";
import PlaceSlider from "../../components/common/placeSlider/placeSlider";

const KakaoMapPage = () => {
  const placeListRef = useRef();
  const searchRef = useRef();
  const inputRef = useRef();
  const sliderRef = useRef();

  const userStates = useRecoilValue(userState);
  const [needLogin, setNeedLogin] = useState(false);
  // recoil과 beautiful-dnd가 concurrent 문제로 충돌이 나서 전역관리와 페이지내 관리 두가지를 모두 해줘야함.
  const [shoppingItems, setShoppingItems] = useState([]);
  const setShoppingItemsRecoil = useSetRecoilState(userCart); // recoil 전역으로 카트를 관리하는 이유 : 매번 API 호출 후 조회하면 네트워크 성능상 Bad, 따라서 호출한 것 처럼 흉내내기 위함.

  const [cartVisible, setCartVisible] = useState(false);
  const [deleteItemId, setDeleteItemId] = useState(null);
  const [place, setPlace] = useState({}); // 현재 선택된 place
  const [inputKeyword, handleInputKeyword] = useInput();
  const [searchPlace, setSearchPlace] = useState(""); // 입력받은 keyword 저장
  const [searchPlaces, setSearchPlaces] = useState([]);
  const [markerIndex, setMarkerIndex] = useState();
  const [openListPortal, setOpenListPortal] = useState();

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
  const handleDeleteItem = useCallback(
    (id) => {
      setShoppingItems((prev) => {
        const updated = prev.filter((item) => item.id !== id);
        return updated;
      });
      onDeleteCart(id);
    },
    [setShoppingItems]
  );

  // 클릭된 카드의 장소로 setting
  const updateClickedPlace = useCallback((place) => {
    setPlace(place);
  }, []);

  const updateSearchPlaces = useCallback((places) => {
    if (places.length > 0) {
      placeListRef.current.style.display = "initial";
    }
    setSearchPlaces(places);
  }, []);

  // keyword 검색
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

  // marker 클릭 시
  const clickedMarker = useCallback((index) => {
    sliderRef.current.slickGoTo(index);
  }, []);

  const onUpdateMarkerIndex = useCallback((index) => {
    setMarkerIndex(index);
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

  const resetCartAll = useCallback(() => {
    setShoppingItems([]);
    onDeleteCartAll();
  }, [setShoppingItems]);

  const portalAuthClose = useCallback(() => {
    setNeedLogin(false);
  }, []);

  const onOpenListPortal = useCallback(() => {
    setOpenListPortal(true);
  }, []);
  const onCloseListPortal = useCallback(() => {
    setOpenListPortal(false);
  }, []);

  useEffect(() => {
    async function receiveCart() {
      const cartItems = await onReceiveCart();
      if (cartItems) {
        setShoppingItems(cartItems);
      }
    }
    if (userStates.isLogin) {
      receiveCart();
    }
  }, [userStates]);

  useEffect(() => {
    setShoppingItemsRecoil(shoppingItems); // 페이지에서 shopping Items state가 변경되면 전역으로도 바꿔줘야함.
  }, [setShoppingItemsRecoil, shoppingItems]);

  return (
    <div className={styles.KakaoMapPage}>
      <div className={styles.searchContainer}>
        <form ref={searchRef} className={styles.form} onSubmit={handleSubmit}>
          <input
            ref={inputRef}
            className={styles.inputBar}
            placeholder="Search place..."
            onChange={handleInputKeyword}
            value={inputKeyword || ""}
          />
          <div className={styles.toggle}>
            <ShoppingCartOutlined className={styles.cartIcon} onClick={setCartVisibleTrue} />
          </div>
        </form>
        <div className={styles.profileToggle}>
          <SideProfileToggle />
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
        <div className={styles.mapButtonBox}>
          <button className={styles.listPortalButton} onClick={onOpenListPortal}>
            <FontAwesomeIcon icon={faList} color="white" size="lg" />
          </button>
        </div>
        <div className={styles.placeSliderContainer}>
          <PlaceSlider
            sliderRef={sliderRef}
            updateClickedPlace={updateClickedPlace}
            onUpdateMarkerIndex={onUpdateMarkerIndex}
            searchPlaces={searchPlaces}
            onHandleDelete={handleDeleteItem}
            addShoppingCart={addShoppingCart}
            shoppingItems={shoppingItems}
          />
        </div>
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
      {!needLogin && (
        <Drawer
          placement="right"
          closable={false}
          visible={cartVisible}
          onClose={setCartVisibleFalse}
          width={window.innerWidth > 768 ? "36vw" : "80vw"}
          bodyStyle={{
            backgroundColor: "#ebecec",
            padding: 0,
          }}
          zIndex={1004}
        >
          <ShoppingCart
            deleteClickedItemId={deleteClickedItemId}
            updateShoppingCart={updateShoppingCart}
            updateMemoShoppingItem={updateMemoShoppingItem}
            resetCartAll={resetCartAll}
            onClose={setCartVisibleFalse}
            items={shoppingItems}
            deleteItemId={deleteItemId}
            onDeleteItem={handleDeleteItem}
          />
        </Drawer>
      )}
      {needLogin && <PortalAuth onClose={portalAuthClose} />}
      {openListPortal && <PortalPlaceList onClose={onCloseListPortal} places={searchPlaces} />}
    </div>
  );
};

export default KakaoMapPage;
