import { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [imageUrl, setImageUrl] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const analyze = async () => {
    if (!imageUrl.trim()) { setError("Entrez une URL."); return; }
    setLoading(true); setError(""); setResult(null);
    try {
      const res = await axios.post(
        `http://localhost:8080/api/analyze?url=${encodeURIComponent(imageUrl)}`
      );
      setResult(res.data);
    } catch (e) { setError("Erreur lors de l'analyse."); }
    setLoading(false);
  };

  const getColor = (s) => s>=80?"#27ae60":s>=60?"#f39c12":s>=40?"#e67e22":"#e74c3c";
  const getLabel = (s) => s>=80?"Très fiable":s>=60?"Probable":s>=40?"Incertaine":"Non déterminée";

  return (
    <div className="app">
      <header className="header">
        <h1>Source Origin</h1>
        <p>Remontez la chaine de publication d'une image</p>
      </header>
      <main className="main">
        <div className="search-box">
          <input type="text" placeholder="URL d'une image..."
            value={imageUrl} onChange={(e) => setImageUrl(e.target.value)}
            className="input" />
          <button onClick={analyze} disabled={loading} className="btn">
            {loading ? "Analyse..." : "Analyser"}
          </button>
        </div>
        {error && <div className="error">{error}</div>}
        {result && (
          <div className="results">
            <div className="score-card" style={{borderColor: getColor(result.confidenceScore)}}>
              <div className="score-number" style={{color: getColor(result.confidenceScore)}}>
                {result.confidenceScore.toFixed(0)}%
              </div>
              <div>{getLabel(result.confidenceScore)}</div>
              <div>{result.totalFound} occurrence(s) trouvée(s)</div>
            </div>
            <h3>Toutes les occurrences</h3>
            {result.occurrences.map((occ, i) => (
              <div key={i} className="occurrence-card">
                <strong>#{i+1} {occ.title}</strong>
                <p>{occ.source} — {occ.date}</p>
                <a href={occ.url} target="_blank" rel="noreferrer">Voir</a>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
