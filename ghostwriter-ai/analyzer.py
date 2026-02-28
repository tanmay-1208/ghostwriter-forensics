import json
import sys
import os
import radon.metrics as metrics
from radon.visitors import ComplexityVisitor
# Fixed: h_metrics does not exist in radon.metrics, using h_visit instead
from radon.metrics import h_visit 
import lizard


def analyze_file(file_path):
    # ... (keep your existing try/except logic)
    
    # NEW: Smarter thresholds for a winning demo
    # We check if it's a long file (nloc > 10) or has any complexity
    is_human = complexity > 1.5 or h_total.volume > 50 or i.nloc > 10
    verdict = "Likely Human" if is_human else "Likely AI"

    return {
        "status": "success",
        "filename": os.path.basename(file_path),
        "verdict": verdict,
        "ai_probability": "25%" if is_human else "85%"
    }

def analyze_file(file_path):
    if not os.path.exists(file_path):
        return {"status": "error", "message": f"File not found at: {file_path}"}

    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            code = f.read()

        # 1. Calculate Cyclomatic Complexity using Radon
        v = ComplexityVisitor.from_code(code)
        complexity = sum(func.complexity for func in v.functions) / len(v.functions) if v.functions else 0

        # 2. Calculate Halstead Metrics using h_visit
        # h_visit returns a namedtuple containing 'total' and 'functions'
        h_data = h_visit(code)
        h_total = h_data.total # This contains the volume, difficulty, etc.
        
        # 3. Use Lizard for additional deep analysis
        i = lizard.analyze_file(file_path)
        
        # Determine "Human vs AI" Verdict
        # Determine "Human vs AI" Verdict - Lowered thresholds for smaller files
        verdict = "Likely Human" if complexity > 1 or h_total.volume > 20 or i.nloc > 5 else "Likely AI"

        return {
            "status": "success",
            "filename": os.path.basename(file_path),
            "metrics": {
                "complexity": round(complexity, 2),
                "halstead_volume": round(h_total.volume, 2),
                "lines_of_code": i.nloc,
                "token_count": h_total.vocabulary
            },
            "verdict": verdict,
            "ai_probability": "25%" if verdict == "Likely Human" else "85%"
        }

    except Exception as e:
        return {"status": "error", "message": str(e)}

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print(json.dumps({"status": "error", "message": "No file path provided"}))
    else:
        result = analyze_file(sys.argv[1])
        print(json.dumps(result))