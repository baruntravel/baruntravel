import styles from "./routeCarousel.module.css";
import { Carousel } from "react-responsive-carousel";

const routeCarousel = ({ routeItems, handleChange }) => {
  return (
    <div className={styles.container}>
      <Carousel showArrows infiniteLoop onChange={(e) => handleChange(e)}>
        {Object.keys(routeItems).map((value, index) => {
          return (
            <div className={styles.routeCard} key={index}>
              <div className={styles.top}>
                <div className={styles.routeName}>
                  {routeItems[value].routeName}
                </div>
                <div className={styles.creator}>
                  {routeItems[value].creator}
                </div>
              </div>
              <div className={styles.bottom}>
                {routeItems[value].places.map((value, index) => {
                  return (
                    <div className={styles.places} key={index}>
                      {value.placeName}
                    </div>
                  );
                })}
              </div>
            </div>
          );
        })}
      </Carousel>
    </div>
  );
};

export default routeCarousel;
