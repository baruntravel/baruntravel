import styles from "./mapButton.module.css";

const MapButton = ({ mapHandler }) => {
  return (
    <div className={styles.container}>
      <button onClick={mapHandler}>map</button>
    </div>
  );
};

export default MapButton;
