import React, { useCallback, useEffect, useState } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import styles from "./routeDetailPage.module.css";
import DetailHeader from "../../components/detailHeader/detailHeader";
import ImageMap from "../../components/map/imageMap/imageMap";
import PlaceCard from "../../components/placeCard/placeCard";
import Avatar from "antd/lib/avatar/avatar";
import ReviewList from "../../components/reviewComponents/reviewList/reviewList";
import ImagesZoom from "../../components/reviewComponents/imagesZoom/imagesZoom";
import { Drawer } from "antd";
import InputRootName from "../../components/common/inputRootName/inputRootName";
import {
  onDeleteRouteReview,
  onEditRouteReview,
  onReceiveRouteReview,
  onUploadRouteReview,
} from "../../api/reviewAPI";
import { userState } from "../../recoil/userState";
import { useRecoilValue } from "recoil";
import PortalAuth from "../../containers/portalAuth/portalAuth";
import { getRouteDetail } from "../../api/routeAPI";
import { StarFilled, UserOutlined } from "@ant-design/icons";
import { useHistory } from "react-router-dom";
import MoreReviewList from "../../components/reviewComponents/moreReviewList/moreReviewList";
import Loading from "../../components/common/loading/loading";

const RouteDetailPage = (props) => {
  const userStates = useRecoilValue(userState);
  const history = useHistory();

  const routeId = window.location.pathname.split("/").pop(); // url 마지막 부분이 ID이다.

  const [loading, setLoading] = useState(true);
  const [showImagesZoom, setShowImagesZoom] = useState(false);
  const [imageIndex, setImageIndex] = useState(0);
  const [imagePlaceName, setImagePlaceName] = useState("");
  const [moreReview, setMoreReview] = useState(false);
  const [openInputName, setOpenInputName] = useState(false);
  const [needLogin, setNeedLogin] = useState(false);
  const [routeDetail, setRouteDetail] = useState({});
  const [images, setImages] = useState([]); // 이미지와 이름을 같이 저장
  const [postImages, setPostImages] = useState([]); // 이미지만 저장 (줌을 위한 이미지)
  const [reviewDatas, setReviewDatas] = useState([]); // 리뷰들을 불러와 저장할 state

  const onCloseInputName = useCallback(() => {
    setOpenInputName(false);
  }, []);
  const onOpenInputName = useCallback(() => {
    setOpenInputName(true);
  }, []);
  const onZoom = useCallback(() => {
    setShowImagesZoom(true);
  }, []);
  const onClose = useCallback(() => {
    setShowImagesZoom(false);
  }, []);
  const onOpenPortalAuth = useCallback(() => {
    setNeedLogin(true);
  }, []);
  const onClosePortalAuth = useCallback(() => {
    setNeedLogin(false);
  }, []);
  const onOpenMoreReview = useCallback(() => {
    setMoreReview(true);
  }, []);
  const onCloseMoreReview = useCallback(() => {
    setMoreReview(false);
  }, []);

  const handleSetReviewDatas = useCallback((updated) => {
    setReviewDatas(updated);
  }, []);
  const onUploadReview = useCallback(
    (formData) => {
      onUploadRouteReview(routeId, formData); // 추후에 root ID 동적으로 받아오는 걸 구현 후 수정
    },
    [routeId]
  );
  const onEditReview = useCallback((reviewId, formData) => {
    onEditRouteReview(reviewId, formData);
  }, []);
  const onDeleteReview = useCallback((id) => {
    onDeleteRouteReview(id);
  }, []);

  const afterSliderChange = useCallback(
    (index) => {
      setImageIndex(index);
      setImagePlaceName(images[index][1]);
    },
    [images]
  );

  useEffect(() => {
    if (!userStates.isLogin) {
      // 로그인 하지않으면 기존 페이지로 이동
      history.push("/");
    }
    async function getRouteDetailInfo() {
      // 루트 상세페이지의 정보를 받아옴
      const route = await getRouteDetail(routeId);
      setRouteDetail(route);
      const route_images =
        route && route.places.map((place) => [place.image, place.name]);
      const images = route_images.filter((item) => item[0]); // 이미지가 존재하는것만 뽑아낸다.
      setPostImages(images.map((img) => img[0])); // 이미지만 뽑아서 저장
      setImages(images); // 이미지와 이름을 세트로 저장
      setImagePlaceName(images[0][1]); // 첫 이미지의 장소 이름 저장
    }
    async function getReviewList() {
      const reviews = await onReceiveRouteReview(routeId);
      setReviewDatas(reviews);
    }
    getRouteDetailInfo();
    getReviewList();
    setLoading(false);
  }, [history, routeId, userStates]);

  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  if (!userStates.isLogin) {
    // 로그인 하지않으면 기존 페이지로 이동
    history.push("/");
  }

  if (loading) {
    return (
      <>
        <Loading />
      </>
    );
  }
  return (
    <div className={styles.RouteDetailPage}>
      <DetailHeader />
      <div className={styles.sliderContainer} onClick={onZoom}>
        <Slider {...settings} afterChange={(index) => afterSliderChange(index)}>
          {images.map((v, index) => (
            <div key={index} className={styles.imageContainer}>
              <img className={styles.img} src={v[0]} alt="upload" />
            </div>
          ))}
        </Slider>
        <div className={styles.slider__placeNameBox}>
          <span className={styles.slider__placeName}>{imagePlaceName}</span>
        </div>
      </div>
      <section className={styles.body}>
        <div className={styles.authorBox}>
          <Avatar
            src={(routeDetail.creator && routeDetail.creator.avatar) || ""}
            size="large"
            style={{
              width: "50px",
              height: "50px",
              border: "2px solid white",
            }}
            icon={<UserOutlined />}
          />
          <div className={styles.nicknameBox}>
            <span className={styles.nickname}>
              {routeDetail.creator && routeDetail.creator.name}
            </span>
            <span
              className={styles.dateReview}
            >{`${routeDetail.createdAt}`}</span>
          </div>
        </div>
        <div className={styles.reviewInfo}>
          <StarFilled style={{ color: "#eb2f96" }} />
          <span className={styles.reviewInfo__score}>{routeDetail.score}</span>
          <span
            className={styles.reviewInfo__reviewCount}
          >{`(${routeDetail.reviewCount})`}</span>
        </div>
        <div className={styles.titleBox}>
          <h2 className={styles.title}>{routeDetail.name}</h2>
        </div>
        <div className={styles.placesContainer}>
          <div className={styles.contentBox}>
            <span className={styles.content}>이 장소를 다녀왔어요</span>
          </div>
          <div className={styles.imageMap}>
            <ImageMap places={routeDetail.places} />
          </div>
          <div className={styles.placesBox}>
            {routeDetail.places &&
              routeDetail.places.map((place, index) => (
                <div key={index} className={styles.placeBox}>
                  <PlaceCard place={place} />
                </div>
              ))}
          </div>
        </div>
        <div className={styles.buttonBox}>
          <button className={styles.button} onClick={onOpenInputName}>
            일정으로 담기
          </button>
          <span className={styles.wishCount}>{`${4}명이 좋아해요`}</span>
        </div>
        <div className={styles.reviewList}>
          <ReviewList
            onOpenPortalAuth={onOpenPortalAuth}
            onDeleteReview={onDeleteReview}
            onUploadReview={onUploadReview}
            onEditReview={onEditReview}
            reviewDatas={reviewDatas}
            setReviewDatas={handleSetReviewDatas}
            userStates={userStates}
          />
        </div>
        <div className={styles.buttonBox}>
          <button
            className={styles.button}
            onClick={onOpenMoreReview}
          >{`${4}개의 리뷰 더보기`}</button>
        </div>
      </section>
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
        <ImagesZoom images={postImages} onClose={onClose} index={imageIndex} />
      )}
      {openInputName && <InputRootName onClose={onCloseInputName} />}
      {needLogin && <PortalAuth onClose={onClosePortalAuth} />}
    </div>
  );
};

export default RouteDetailPage;
