# BlockID: Blockchain-Based Identity Verification System

BlockID is a full-stack decentralized identity management system built with **Java (Spring Boot)** and **React**. It utilizes blockchain principles to ensure that identity credentials are encrypted, immutable, and easily verifiable by third parties.

## 🚀 Key Features
- **Blockchain Chaining:** Every identity record is cryptographically linked to the previous block using SHA-256 hashes.
- **AES-256 Encryption:** Sensitive user data is encrypted before being stored in the PostgreSQL database.
- **Digital Signatures:** Admin approvals generate a unique cryptographic signature to prove authority.
- **Audit Trail:** Module 6 implementation that tracks every verification attempt for security monitoring.
- **Privacy-Preserving:** The public ledger (Explorer) shows cryptographic proofs without exposing private user data.

## 🛠️ Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security (JWT)
- **Frontend:** React 18 (Vite), Axios, CSS3 (Modern Orange & White Theme)
- **Database:** PostgreSQL
- **Security:** SHA-256 Hashing, AES Symmetric Encryption

## ⚙️ How to Run
1. **Database:** Create a PostgreSQL database named `blockid`.
2. **Backend:** 
   - Update `application.properties` with your DB credentials.
   - Run `mvn spring-boot:run`.
3. **Frontend:**
   - Navigate to the `frontend` folder.
   - Run `npm install` then `npm run dev`.
4. **Access:** Open `http://localhost:5173`.
