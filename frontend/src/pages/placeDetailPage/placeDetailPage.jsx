import React, { useCallback, useEffect, useState } from "react";
import DetailHeader from "../../components/detailHeader/detailHeader";
import styles from "./placeDetailPage.module.css";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { StarFilled } from "@ant-design/icons";
import { Drawer } from "antd";
import ReviewForm from "../../components/reviewComponents/reviewForm/reviewForm";
import ReviewList from "../../components/reviewComponents/reviewList/reviewList";
import ImagesZoom from "../../components/reviewComponents/imagesZoom/imagesZoom";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import { getPlaceDetail } from "../../api/placeAPI";

const { kakao } = window;

const PlaceDetailPage = (props) => {
  const userStates = useRecoilValue(userState);
  const [placeDetail, setPlaceDetail] = useState({});
  const [loading, setLoading] = useState(true);
  const [needLogin, setNeedLogin] = useState(false);
  const [reviewWrite, setReviewWrite] = useState(false);
  const [showImagesZoom, setShowImagesZoom] = useState(false);
  const [imageIndex, setImageIndex] = useState(0);
  const [liked, setLiked] = useState(false);
  const [images, setImages] = useState([]);

  const placeId = 7855876;

  const onZoom = useCallback(() => {
    setShowImagesZoom(true);
  }, []);
  const onCloseZoom = useCallback(() => {
    setShowImagesZoom(false);
  }, []);
  const onClickReviewWrite = useCallback(() => {
    if (userStates.isLogin) {
      setReviewWrite(true);
    } else {
      setNeedLogin(true);
    }
  }, [userStates]);
  const onCloseReviewWrite = useCallback(() => {
    setReviewWrite(false);
  }, []);
  const afterSliderChange = useCallback((index) => {
    setImageIndex(index);
  }, []);
  const portalAuthClose = useCallback(() => {
    setNeedLogin(false);
  }, []);
  const portalAuthOpen = useCallback(() => {
    setNeedLogin(true);
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

  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  useEffect(() => {
    // placeDetail 받아오는 함수
    async function getPlaceDetailInfo() {
      const placeDetailInfo = await getPlaceDetail(placeId);
      if (placeDetailInfo) {
        setPlaceDetail(placeDetailInfo);
        setImages(placeDetailInfo.images.map((item) => item.url));
      }
    }

    // map image 만드는 함수
    function makeMapImage(x, y) {
      let markerPosition = new kakao.maps.LatLng(y, x);
      let marker = {
        position: markerPosition,
      };
      let staticMapContainer = document.getElementById("staticMap"),
        staticMapOption = {
          center: new kakao.maps.LatLng(y, x),
          level: 4,
          marker: marker,
        };
      let staticMap = new kakao.maps.StaticMap(
        staticMapContainer,
        staticMapOption
      );
    }
    // placeDetail 받아오는 곳
    getPlaceDetailInfo();
    window.scrollTo(0, 0);
    setLoading(false);
  }, []);

  const [reviewDatas, setReviewDatas] = useState([
    {
      createdAt: new Date(2011, 0, 1, 0, 0, 0, 0),
      likeCount: 5,
    },
    {
      createdAt: new Date(2011, 0, 1, 0, 0, 0, 2),
      likeCount: 6,
    },
    {
      createdAt: new Date(2011, 0, 1, 0, 0, 0, 1),
      likeCount: 7,
    },
    {
      createdAt: new Date(2011, 0, 1, 0, 0, 0, 4),
      likeCount: 3,
    },
  ]);

  if (loading) {
    return <div>로딩</div>;
  }
  if (placeDetail) {
    return <div>place 정보가 없습니다.</div>;
  }
  return (
    <div className={styles.PlaceDetailPage}>
      <DetailHeader
        liked={liked}
        needLogin={needLogin}
        portalAuthOpen={portalAuthOpen}
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
          <span>{`${imageIndex + 1} / ${images.length}`}</span>
        </div>
      </div>
      <div className={styles.body}>
        <div className={styles.body__header}>
          <h1 className={styles.body__placeName}>{placeDetail.name}</h1>
          <div className={styles.body__placeInfo}>
            <div className={styles.body__rate}>
              <StarFilled style={{ color: "#eb2f96" }} />
              <span className={styles.body__score}>{placeDetail.score}</span>
              <span
                className={styles.body__reviewCount}
              >{`(${placeDetail.likeCount})`}</span>
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
            <div id="staticMap" className={styles.body__map}></div>
          </div>
        </div>
        <div className={styles.reviewList}>
          <ReviewList
            reviewDatas={reviewDatas}
            onClickReviewWrite={onClickReviewWrite}
          />
        </div>
      </div>
      <Drawer
        visible={reviewWrite}
        placement="bottom"
        height="100vh"
        bodyStyle={{ padding: 0 }}
        onClose={onCloseReviewWrite}
      >
        <ReviewForm />
      </Drawer>
      {showImagesZoom && (
        <ImagesZoom images={images} onClose={onCloseZoom} index={imageIndex} />
      )}
      {needLogin && <PortalAuth onClose={portalAuthClose} />}
    </div>
  );
};

export default PlaceDetailPage;
