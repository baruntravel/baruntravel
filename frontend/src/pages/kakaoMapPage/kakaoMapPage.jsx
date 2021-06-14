import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./kakaoMapPage.module.css";
import HotplaceMap from "../../components/kakaoMapPage/kakaoMap/kakaoMap";
import useInput from "../../hooks/useInput";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faList } from "@fortawesome/free-solid-svg-icons";
import CategoryBar from "../../components/common/categoryBar/categoryBar";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { useRecoilValue } from "recoil";
import { userState } from "../../recoil/userState";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import SideProfileToggle from "../../components/common/sideProfileToggle/sideProfileToggle";
import PortalPlaceList from "../../components/portal/portalPlaceList/portalPlaceList";
import PlaceSlider from "../../components/common/placeSlider/placeSlider";
import WishListPortal from "../../components/portal/wishListPortal/wishListPortal";
import AddSuccessConfirm from "../../components/common/addSuccessConfirm/addSuccessConfirm";
import RemoveSuccessConfirm from "../../components/common/removeSuccessConfirm/removeSuccessConfirm";

const KakaoMapPage = () => {
  const placeListRef = useRef();
  const searchRef = useRef();
  const inputRef = useRef();
  const sliderRef = useRef();

  const userStates = useRecoilValue(userState);
  const [needLogin, setNeedLogin] = useState(false);

  const [place, setPlace] = useState({}); // 현재 선택된 place
  const [inputKeyword, handleInputKeyword] = useInput();
  const [searchPlace, setSearchPlace] = useState(""); // 입력받은 keyword 저장
  const [searchPlaces, setSearchPlaces] = useState([]);
  const [markerIndex, setMarkerIndex] = useState();
  const [openListPortal, setOpenListPortal] = useState(false);
  const [openWishPortal, setOpenWishPortal] = useState(false);
  const [openAddSuccessConfirm, setOpenAddSuccessConfirm] = useState(false);
  const [openDelSuccessConfirm, setOpenDelSuccessConfirm] = useState(false);

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

  const portalAuthClose = useCallback(() => {
    setNeedLogin(false);
  }, []);

  const onOpenListPortal = useCallback(() => {
    setOpenListPortal(true);
  }, []);
  const onCloseListPortal = useCallback(() => {
    setOpenListPortal(false);
  }, []);

  const onOpenWishListPortal = useCallback(() => {
    setOpenWishPortal(true);
  }, []);
  const onCloseWishListPortal = useCallback(() => {
    setOpenWishPortal(false);
  }, []);

  const onOpenAddSuccess = useCallback(() => {
    setOpenAddSuccessConfirm(true);
  }, []);

  const onCloseAddSuccess = useCallback(() => {
    setOpenAddSuccessConfirm(false);
  }, []);

  const onOpenDeleteSuccess = useCallback(() => {
    setOpenDelSuccessConfirm(true);
  }, []);

  const onCloseDeleteSuccess = useCallback(() => {
    setOpenDelSuccessConfirm(false);
  }, []);

  const onClickEmptyHeart = useCallback(
    (place) => {
      onOpenWishListPortal();
      setTimeout(() => {
        console.log("추가 api 호출 및 업데이트");
        onOpenAddSuccess();
      }, 1000);
    },
    [onOpenAddSuccess, onOpenWishListPortal]
  );

  const onClickFullHeart = useCallback(
    (place) => {
      console.log(place);
      console.log("삭제 api 호출 및 업데이트");
      onOpenDeleteSuccess();
    },
    [onOpenDeleteSuccess]
  );
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
          <button
            className={styles.listPortalButton}
            onClick={onOpenListPortal}
          >
            <FontAwesomeIcon icon={faList} color="white" size="lg" />
          </button>
        </div>
        <div className={styles.placeSliderContainer}>
          <PlaceSlider
            sliderRef={sliderRef}
            updateClickedPlace={updateClickedPlace}
            onUpdateMarkerIndex={onUpdateMarkerIndex}
            searchPlaces={searchPlaces}
            onClickEmptyHeart={onClickEmptyHeart}
            onClickFullHeart={onClickFullHeart}
          />
        </div>
      </div>
      <div className={styles.categoryContainer}>
        <CategoryBar />
      </div>
      {needLogin && <PortalAuth onClose={portalAuthClose} />}
      {openListPortal && (
        <PortalPlaceList onClose={onCloseListPortal} places={searchPlaces} />
      )}
      {!needLogin && openWishPortal && (
        <WishListPortal onClose={onCloseWishListPortal} />
      )}
      {openAddSuccessConfirm && (
        <AddSuccessConfirm onClose={onCloseAddSuccess} />
      )}
      {openDelSuccessConfirm && (
        <RemoveSuccessConfirm onClose={onCloseDeleteSuccess} />
      )}
    </div>
  );
};

export default KakaoMapPage;
