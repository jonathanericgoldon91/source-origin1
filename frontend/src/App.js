import { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [imageFile, setImageFile] = useState(null);
  const [preview, setPreview] = useState(null);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImageFile(file);
      setPreview(URL.createObjectURL(file));
      setResult(null);
      setError("");
    }
  };

  const analyze = async () => {
    if (!imageFile) {
      setError("Veuillez sélectionner une image.");
      return;
    }
    setLoading(true);
    setError("");
    setResult(null);

    const formData = new FormData();
    formData.append("image", imageFile);

    try {
      const res = await axios.post(
        "http://localhost:8080/api/analyze",
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
      );
      setResult(res.data);
    } catch (e) {
      setError("Erreur lors de l'analyse. Vérifiez le serveur.");
    }
    setLoading(false);
  };

  const getColor = (s) =>
    s >= 80 ? "#27ae60" : s >= 60 ? "#f39c12" : s >= 40 ? "#e67e22" : "#e74c3c";

  const getLabel = (s) =>
    s >= 80 ? "Origine très fiable" :
    s >= 60 ? "Origine probable" :
    s >= 40 ? "Origine incertaine" : "Origine non déterminée";

  return (
    <div className="app">
      <header className="header">
        <h1>🔍 Source Origin</h1>
        <p>Remontez la chaîne de publication d'une image</p>
      </header>

      <main className="main">

        {/* Zone upload image */}
        <div className="upload-box">
          <label className="upload-label" htmlFor="imageInput">
            {preview ? (
              <img src={preview} alt="preview" className="preview-img" />
            ) : (
              <div className="upload-placeholder">
                <span>📁</span>
                <p>Cliquez pour choisir une image</p>
                <small>JPEG, PNG, WebP — max 10MB</small>
              </div>
            )}
          </label>
          <input
            id="imageInput"
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            style={{ display: "none" }}
          />
        </div>

        {imageFile && (
          <div className="file-info">
            📎 {imageFile.name} ({(imageFile.size / 1024).toFixed(0)} Ko)
          </div>
        )}

        <button
          onClick={analyze}
          disabled={loading || !imageFile}
          className="btn"
        >
          {loading ? "⏳ Analyse en cours..." : "🔍 Analyser l'image"}
        </button>

        {error && <div className="error">{error}</div>}

        {result && (
          <div className="results">

            {/* Score de confiance */}
            <div
              className="score-card"
              style={{ borderColor: getColor(result.confidenceScore) }}
            >
              <div
                className="score-number"
                style={{ color: getColor(result.confidenceScore) }}
              >
                {result.confidenceScore.toFixed(0)}%
              </div>
              <div className="score-label">
                {getLabel(result.confidenceScore)}
              </div>
              <div className="score-info">
                {result.totalFound} occurrence(s) trouvée(s) sur le web
              </div>
            </div>

            {/* Première occurrence */}
            {result.firstOccurrenceUrl && (
              <div className="first-occurrence">
                <h3>🥇 Première occurrence détectée</h3>
                <p><strong>Source :</strong> {result.firstOccurrenceSource}</p>
                <a
                  href={result.firstOccurrenceUrl}
                  target="_blank"
                  rel="noreferrer"
                >
                  Voir la source originale →
                </a>
              </div>
            )}

            {/* Timeline des occurrences */}
<h3>📅 Chaîne de publication</h3>
            <div className="timeline">
              {result.occurrences.map((occ, i) => (
                <div key={i} className="timeline-item">
                  <div className="timeline-dot">
                    {i === 0 ? "🥇" : `#${i + 1}`}
                  </div>
                  <div className="timeline-content">
                    <strong>{occ.title}</strong>
                    <span className="timeline-source">{occ.source}</span>
                    <span className="timeline-date">📅 {occ.date}</span>
                    <a
                      href={occ.url}
                      target="_blank"
                      rel="noreferrer"
                    >
                      Voir →
                    </a>
                  </div>
                </div>
              ))}
            </div>

          </div>
        )}
      </main>
    </div>
  );
}

export default App;
