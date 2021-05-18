import styles from "./sortBox.module.css";

const SortBox = () => {
  return (
    <div className={styles.container}>
      <button className={styles.newestButton}>최신순</button>
      <button className={styles.bestButton}>추천순</button>
    </div>
  );
};

export default SortBox;
