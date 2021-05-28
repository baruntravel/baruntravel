import styles from "./routeCarousel.module.css";
import Slider from "react-slick";
import FavoriteBorderIcon from "@material-ui/icons/FavoriteBorder"; // empty heart icon
import FavoriteIcon from "@material-ui/icons/Favorite"; // filled heart icon
import { Link } from "react-router-dom";

const RouteCarousel = ({ routes, indexHandler, initialIndex }) => {
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    initialSlide: initialIndex,
  };

  return (
    <div className={styles.container}>
      <Slider afterChange={(e) => indexHandler(e)} {...settings}>
        {Object.keys(routes).map((value, index) => {
          return (
            <Link to={`route/${value}`} key={index}>
              <div className={styles.routeCard}>
                <div className={styles.row1}>
                  <div className={styles.routeName}>{routes[value].routeName}</div>
                  <div className={styles.creator}>{routes[value].creator}</div>
                  <button className={styles.heartButton}>
                    <FavoriteBorderIcon />
                  </button>
                </div>
                <div className={styles.row2}>
                  {routes[value].places.map((value, index) => {
                    return (
                      <div className={styles.col1} key={index}>
                        <div className={styles.index}>{index + 1}</div>
                        <div className={styles.places}>{value.placeName}</div>
                      </div>
                    );
                  })}
                </div>
              </div>
            </Link>
          );
        })}
      </Slider>
    </div>
  );
};

export default RouteCarousel;
