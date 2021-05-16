import React, { useCallback, useEffect, useState } from "react";
import DetailHeader from "../../components/detailHeader/detailHeader";
import styles from "./placeDetailPage.module.css";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { StarFilled } from "@ant-design/icons";
import { Drawer } from "antd";
import ReviewList from "../../components/reviewComponents/reviewList/reviewList";
import ImagesZoom from "../../components/reviewComponents/imagesZoom/imagesZoom";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import { onReceivePlace } from "../../api/placeAPI";
import { onReceivePlaceReview, onUploadPlaceReview } from "../../api/reviewAPI";
import { useHistory } from "react-router-dom";
import MoreReviewList from "../../components/reviewComponents/moreReviewList/moreReviewList";

const { kakao } = window;
const PlaceDetailPage = (props) => {
  const userStates = useRecoilValue(userState);
  const history = useHistory();
  const [needLogin, setNeedLogin] = useState(false);
  const [showImagesZoom, setShowImagesZoom] = useState(false);
  const [imageIndex, setImageIndex] = useState(0);
  const [liked, setLiked] = useState(false);
  const [images, setImages] = useState([]); // 보여줄 메인 이미지 저장소
  const [placeDetail, setPlaceDetail] = useState({});
  const [reviewDatas, setReviewDatas] = useState([]); // 리뷰들을 불러와 저장할 state
  const [moreReview, setMoreReview] = useState(false);

  // test 용 place id
  const placeId = 7855876;

  const onZoom = useCallback(() => {
    setShowImagesZoom(true);
  }, []);
  const onCloseZoom = useCallback(() => {
    setShowImagesZoom(false);
  }, []);

  const onClosePortalAuth = useCallback(() => {
    setNeedLogin(false);
  }, []);
  const onOpenPortalAuth = useCallback(() => {
    setNeedLogin(true);
  }, []);

  const onOpenMoreReview = useCallback(() => {
    setMoreReview(true);
  }, []);
  const onCloseMoreReview = useCallback(() => {
    setMoreReview(false);
  }, []);

  const onClickLike = useCallback(() => {
    if (userStates.isLogin) {
      console.log("좋아요 API 호출");
      setLiked(true);
    } else {
      setNeedLogin(true);
    }
  }, [userStates]);
  const onClickUnlike = useCallback(() => {
    console.log("좋아요 취소 API 호출");
    setLiked(false);
  }, []);

  const handleSetReviewDatas = useCallback((updated) => {
    setReviewDatas(updated);
  }, []);
  const onUploadReview = useCallback((formData) => {
    onUploadPlaceReview(placeId, formData);
  }, []);

  const afterSliderChange = useCallback((index) => {
    setImageIndex(index);
  }, []);
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  useEffect(() => {
    async function getPlaceDetail() {
      // place 디테일 정보를 불러오는 함수
      const placeDetailInfo = await onReceivePlace(placeId);
      if (placeDetailInfo) {
        const images = placeDetailInfo.images.map((image) => image.url);
        setPlaceDetail(placeDetailInfo);
        setImages(images);
      }
    }
    async function getReviewList() {
      // 해당 place의 리뷰를 받아오는 함수
      const reviews = await onReceivePlaceReview(placeId);
      setReviewDatas(reviews);
    }
    getPlaceDetail();
    getReviewList();

    let markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    let marker = {
      position: markerPosition,
    };
    let staticMapContainer = document.getElementById("staticMap"),
      staticMapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 4,
        marker: marker,
      };
    let staticMap = new kakao.maps.StaticMap(
      staticMapContainer,
      staticMapOption
    );
    window.scrollTo(0, 0);
  }, []);

  if (!userStates.isLogin) {
    // 로그인 하지 않았을 때 접근 불가 -> 추후에 API 수정 후 고쳐야됨
    history.push("/");
  }
  return (
    <div className={styles.PlaceDetailPage}>
      <DetailHeader
        liked={liked}
        needLogin={needLogin}
        onOpenPortalAuth={onOpenPortalAuth}
        onClickLike={onClickLike}
        onClickUnlike={onClickUnlike}
      />
      <div className={styles.slideContainer} onClick={onZoom}>
        <Slider {...settings} afterChange={(index) => afterSliderChange(index)}>
          {images.map((imgSrc, index) => (
            <div key={index} className={styles.imageContainer}>
              <img className={styles.image} src={imgSrc} alt="placeImage" />
            </div>
          ))}
        </Slider>
        <div className={styles.imageCounter}>
          <span>
            {imageIndex + 1} / {images.length}
          </span>
        </div>
      </div>
      <div className={styles.body}>
        <div className={styles.body__header}>
          <h1 className={styles.body__placeName}>{placeDetail.name}</h1>
          <div className={styles.body__placeInfo}>
            <div className={styles.body__rate}>
              <StarFilled style={{ color: "#eb2f96" }} />
              <span className={styles.body__score}>{placeDetail.score}</span>
              <span className={styles.body__reviewCount}>
                ({reviewDatas.length})
              </span>
            </div>
            <span className={styles.body__address}>{placeDetail.address}</span>
          </div>
        </div>
        <div className={styles.body__placeLocation}>
          <div className={styles.body__titleContainer}>
            <h2 className={styles.body__locationTitle}>위치</h2>
          </div>
          <div className={styles.body__addressContainer}>
            <span className={styles.body__placeAddress}>
              {placeDetail.address}
            </span>
          </div>
          <div className={styles.body__mapContainer}>
            <div id="staticMap" className={styles.body__map} />
          </div>
        </div>
        <div className={styles.buttonBox}>
          <button className={styles.button}>장바구니 카트에 담기</button>
          <span className={styles.wishCount}>{`${4}명이 좋아해요`}</span>
        </div>
        <div className={styles.reviewList}>
          <ReviewList
            userStates={userStates}
            reviewDatas={reviewDatas}
            onOpenPortalAuth={onOpenPortalAuth}
            onUploadReview={onUploadReview}
          />
        </div>
        <div className={styles.buttonBox}>
          <button
            className={styles.button}
            onClick={onOpenMoreReview}
          >{`${4}개의 리뷰 더보기`}</button>
        </div>
      </div>
      <Drawer // 리뷰 더보기
        className={styles.reviewListDrawer}
        visible={moreReview}
        placement="right"
        width="100vw"
        bodyStyle={{ padding: 0 }}
        closeIcon={false}
        style={{
          overflowY: "hidden",
        }}
      >
        <MoreReviewList
          setReviewDatas={handleSetReviewDatas}
          onCloseMoreReview={onCloseMoreReview}
          reviewDatas={reviewDatas}
        />
      </Drawer>
      {showImagesZoom && (
        <ImagesZoom images={images} onClose={onCloseZoom} index={imageIndex} />
      )}
      {needLogin && <PortalAuth onClose={onClosePortalAuth} />}
    </div>
  );
};

export default PlaceDetailPage;
